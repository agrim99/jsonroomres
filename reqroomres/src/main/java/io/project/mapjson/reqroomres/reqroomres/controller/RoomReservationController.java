package io.project.mapjson.reqroomres.reqroomres.controller;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import io.project.mapjson.reqroomres.reqroomres.converter.StringMapConverter;
import io.project.mapjson.reqroomres.reqroomres.model.RoomReservation;
import io.project.mapjson.reqroomres.reqroomres.model.RoomReservationDelete;
import io.project.mapjson.reqroomres.reqroomres.model.RoomReservationRequest;
import io.project.mapjson.reqroomres.reqroomres.repository.RoomReservationRepository;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableCaching
public class RoomReservationController {

    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private StringMapConverter stringMapConverter;


    RedissonClient reddisson = Redisson.create();
    RMap<String, Map<String, Double>> map = reddisson.getMap("myMap");



    @PostMapping("room_reservations")
    public String createRoomReservation(@RequestBody RoomReservation roomReservation){


        roomReservationRepository.save(roomReservation);

        return "Saved Successfully";


    }




    @GetMapping("room_reservations")
    public Double getRoomReservationPrice(@RequestBody RoomReservationRequest roomReservationRequest){


        String cache_key = roomReservationRequest.getKeyForCache();

        Integer occupancy = roomReservationRequest.getOccupancy();
        String occupancy_as_string = occupancy.toString();

        if(map.containsKey(cache_key)){
            if(map.get(cache_key).containsKey(occupancy_as_string)){
                return map.get(cache_key).get(occupancy_as_string);
            }
            else{
                String comp = "0";
                Double ans = 0.0;
                for(String key : map.get(cache_key).keySet()){
                    if(key.compareTo(comp) > 0){
                        comp = key;
                        ans = map.get(cache_key).get(key);
                    }
                }
                return ans;
            }
        }


        List<String> priceslist = roomReservationRepository.getPrice(roomReservationRequest.getHotel_id(),
                roomReservationRequest.getDate(), roomReservationRequest.getRoom_category_id());


        if(priceslist.size() == 0){
            return -1.0;
        }

        String prices_string = priceslist.get(0);
        Map<String, Double> prices = stringMapConverter.convertToEntityAttribute(prices_string);


        if(prices.containsKey(occupancy_as_string)){
            Double cache_value = prices.get(occupancy_as_string);
            map.put(cache_key, prices);
            return cache_value;
        }
        else{
            String comp = "0";
            Double ans = 0.0;
            for(String key : prices.keySet()){
                if(key.compareTo(comp) > 0){
                    comp = key;
                    ans = prices.get(key);
                }
            }
            map.put(cache_key, prices);
            return ans;
        }


    }


    @PutMapping("room_reservations")
    public String updateRoomReservation(@RequestBody RoomReservation roomReservation){

/*
        roomReservationRepository.updatePrice(roomReservation.getHotel_id(),
                roomReservation.getDate(), roomReservation.getRoom_category_id(),
                roomReservation.getOccupancy_to_price());

        if(map.containsKey(roomReservation.getKeyForCache())) {
            map.put(roomReservation.getKeyForCache(), roomReservation.getOccupancy_to_price());
        }

        return "Updated Successfully";
*/



        roomReservationRepository.deleterow(roomReservation.getHotel_id(), roomReservation.getDate(),
                roomReservation.getRoom_category_id());

        if(map.containsKey(roomReservation.getKeyForCache())){
            map.put(roomReservation.getKeyForCache(), roomReservation.getOccupancy_to_price());
        }

        roomReservationRepository.save(roomReservation);

        return "Updated Successfully";




    }


    @DeleteMapping("room_reservations")
    public String deleteRoomReservation(@RequestBody RoomReservationDelete roomReservationDelete){


        roomReservationRepository.deleterow(roomReservationDelete.getHotel_id(),
                roomReservationDelete.getDate(), roomReservationDelete.getRoom_category_id());

        map.remove(roomReservationDelete.getKeyForCache());

        return "Deleted Successfully";


    }




}
