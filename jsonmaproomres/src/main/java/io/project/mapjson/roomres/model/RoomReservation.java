package io.project.mapjson.roomres.model;

import io.project.mapjson.roomres.converter.StringMapConverter;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id")
    private Long hotel_id;

    @Column(name = "date")
    private Date date;

    @Column(name = "room_category_id")
    private String room_category_id;

    @Column(name = "occupancy_to_price", columnDefinition = "JSON")
    @Convert(converter = StringMapConverter.class)
    private Map<String, Double> occupancy_to_price;



    public RoomReservation() {

    }


    public RoomReservation(Long hotel_id, Date date, String room_category_id, Map<String, Double> occupancy_to_price) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.room_category_id = room_category_id;
        this.occupancy_to_price = occupancy_to_price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoom_category_id() {
        return room_category_id;
    }

    public void setRoom_category_id(String room_category_id) {
        this.room_category_id = room_category_id;
    }

    public Map<String, Double> getOccupancy_to_price() {
        return occupancy_to_price;
    }

    public void setOccupancy_to_price(Map<String, Double> occupancy_to_price) {
        this.occupancy_to_price = occupancy_to_price;
    }



}