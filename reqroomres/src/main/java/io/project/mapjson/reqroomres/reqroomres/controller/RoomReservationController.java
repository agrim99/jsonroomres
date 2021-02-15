package io.project.mapjson.reqroomres.reqroomres.controller;

import io.project.mapjson.reqroomres.reqroomres.dto.RoomReservationDto;
import io.project.mapjson.reqroomres.reqroomres.dto.RoomReservationDto2;
import io.project.mapjson.reqroomres.reqroomres.model.RoomReservation;
import io.project.mapjson.reqroomres.reqroomres.service.RoomReservationService;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@EnableCaching
public class RoomReservationController {

    @Autowired
    private RoomReservationService roomReservationService;


    RedissonClient reddisson = Redisson.create();
    RMap<String, Map<String, Double>> map = reddisson.getMap("myMap");



    @RequestMapping(method= RequestMethod.POST, value="room_reservations")
    public String createRoomReservation(@RequestBody RoomReservation roomReservation){

        return roomReservationService.save(roomReservation);

    }




    @RequestMapping("hotel_reservations")
    public Double getRoomReservationPrice(@RequestBody RoomReservationDto roomReservationDto){

        return roomReservationService.get(roomReservationDto);


    }


    @RequestMapping(method=RequestMethod.PUT, value = "hotel_reservations")
    public String updateRoomReservation(@RequestBody RoomReservation roomReservation){

        return roomReservationService.update(roomReservation);

    }


    @RequestMapping(method=RequestMethod.DELETE, value = "hotel_reservations")
    public String deleteRoomReservation(@RequestBody RoomReservationDto2 roomReservationDto2){

        return roomReservationService.delete(roomReservationDto2);


    }




}
