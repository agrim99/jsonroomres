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


    //public static final String HASH_KEY = "RoomPrice";


    RedissonClient reddisson = Redisson.create();
    RMap<String, Map<String, Double>> map = reddisson.getMap("myMap");



    /*@PostMapping("room_reservations")
    public String createRoomReservation(@RequestBody RoomReservation roomReservation){
//
//        Date date = roomReservation.getDate();
//        date.setTime(1610908200);
//
//        roomReservation.setDate(date);



        roomReservationRepository.save(roomReservation);

        return "Saved Successfully";


    }*/




    @GetMapping("room_reservations")
    //@Cacheable(key = "#roomReservationRequest.getKeyForCache()", value = HASH_KEY)
    public Double getRoomReservationPrice(@RequestBody RoomReservationRequest roomReservationRequest){

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(roomReservationRequest.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //System.out.println(map.size());

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
                date, roomReservationRequest.getRoom_category_id());


        if(priceslist.size() == 0){
//            Map<String, Double> value_map = new HashMap<>();
//            value_map.put(occupancy_as_string, -1.0);
//            map.put(cache_key, value_map);
//            System.out.println(9090);
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


        //return -1.0;

    }


    @PutMapping("room_reservations")
    //@CachePut(key = "#roomReservation.getKeyForCache()", value = HASH_KEY)
    public String updateRoomReservation(@RequestBody RoomReservation roomReservation){

        if(map.containsKey(roomReservation.getKeyForCache())){
            map.put(roomReservation.getKeyForCache(), roomReservation.getOccupancy_to_price());
        }

        //String s = DatatypeConverter.printBase64Binary(roomReservation.getOccupancy_to_price());

//        String s = stringMapConverter.convertToDatabaseColumn(roomReservation.getOccupancy_to_price());
//        Map<String, Double> occupancy_to_price = stringMapConverter.convertToEntityAttribute(s);
//
//        Map<String, Double> occupancy_to_price = new HashMap<>();
//        occupancy_to_price.put("1",898989.0);
//        occupancy_to_price.put("2",989898.0);
//        occupancy_to_price.put("3",787878.0);



        roomReservationRepository.updatePrice(roomReservation.getHotel_id(),
                roomReservation.getDate(), roomReservation.getRoom_category_id(),
                roomReservation.getOccupancy_to_price());

        //return roomReservation.getOccupancy_to_price();
        return "Updated Successfully";



//        String datestr = roomReservation.getDate().toString();
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("dd/MM/yyyy").parse(datestr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        //String strdate = "14/01/2021";
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("dd/MM/yyyy").parse(roomReservation.getDate());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


/*
        roomReservationRepository.deleterow(roomReservation.getHotel_id(), roomReservation.getDate(),
                roomReservation.getRoom_category_id());

        roomReservationRepository.save(roomReservation);

        //return roomReservation.getOccupancy_to_price();
        return "Updated Successfully";
*/

    }


    @DeleteMapping("room_reservations")
    //@CacheEvict(key = "#roomReservationRequest.getKeyForCache()", value = HASH_KEY)
    public String deleteRoomReservation(@RequestBody RoomReservationDelete roomReservationDelete){

        map.remove(roomReservationDelete.getKeyForCache());

        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(roomReservationDelete.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        roomReservationRepository.deleterow(roomReservationDelete.getHotel_id(),
                date, roomReservationDelete.getRoom_category_id());

        return "Deleted Successfully";

    }






}
