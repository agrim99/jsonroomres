package io.project.mapjson.reqroomres.reqroomres.service;

import io.project.mapjson.reqroomres.reqroomres.converter.StringMapConverter;
import io.project.mapjson.reqroomres.reqroomres.dto.RoomReservationDto;
import io.project.mapjson.reqroomres.reqroomres.dto.RoomReservationDto2;
import io.project.mapjson.reqroomres.reqroomres.model.RoomReservation;
import io.project.mapjson.reqroomres.reqroomres.repository.RoomReservationRepository;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RoomReservationService {


    @Autowired
    private RoomReservationRepository roomReservationRepository;

    @Autowired
    private StringMapConverter stringMapConverter;


    RedissonClient reddisson = Redisson.create();
    RMap<String, Map<String, Double>> map = reddisson.getMap("myMap");



    public String save(RoomReservation roomReservation){


        roomReservationRepository.save(roomReservation);

        return "Saved Successfully";


    }




    public Double get(RoomReservationDto roomReservationDto){


        String cache_key = roomReservationDto.getKeyForCache();

        Integer occupancy = roomReservationDto.getOccupancy();
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


        List<String> priceslist = roomReservationRepository.getPrice(roomReservationDto.getHotelId(),
                roomReservationDto.getDate(), roomReservationDto.getRoomCategoryId());


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


    public String update(RoomReservation roomReservation){

/*
        roomReservationRepository.updatePrice(roomReservation.getHotel_id(),
                roomReservation.getDate(), roomReservation.getRoom_category_id(),
                roomReservation.getOccupancy_to_price());

        if(map.containsKey(roomReservation.getKeyForCache())) {
            map.put(roomReservation.getKeyForCache(), roomReservation.getOccupancy_to_price());
        }

        return "Updated Successfully";
*/



        roomReservationRepository.deleterow(roomReservation.getHotelId(), roomReservation.getDate(),
                roomReservation.getRoomCategoryId());

        roomReservationRepository.save(roomReservation);

        if(map.containsKey(roomReservation.getKeyForCache())){
            map.put(roomReservation.getKeyForCache(), roomReservation.getOccupancyToPrice());
        }


        return "Updated Successfully";




    }


    public String delete(RoomReservationDto2 roomReservationDto2){


        roomReservationRepository.deleterow(roomReservationDto2.getHotelId(),
                roomReservationDto2.getDate(), roomReservationDto2.getRoomCategoryId());

        map.remove(roomReservationDto2.getKeyForCache());

        return "Deleted Successfully";


    }


}
