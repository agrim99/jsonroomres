package io.project.mapjson.reqroomres.reqroomres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationRequest {

    private Long hotel_id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    private String room_category_id;

    private Integer occupancy;


    public RoomReservationRequest() {

    }

    public RoomReservationRequest(Long hotel_id, Date date, String room_category_id) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.room_category_id = room_category_id;
    }

    public RoomReservationRequest(Long hotel_id, Date date, String room_category_id, Integer occupancy) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.room_category_id = room_category_id;
        this.occupancy = occupancy;
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

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }


    public String getKeyForCache() {

        return hotel_id.toString() + "@" + date.toString() + "@" + room_category_id;

    }
}
