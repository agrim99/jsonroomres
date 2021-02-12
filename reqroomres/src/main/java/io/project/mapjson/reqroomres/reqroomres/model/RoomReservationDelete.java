package io.project.mapjson.reqroomres.reqroomres.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RoomReservationDelete {

    private Long hotel_id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    private String room_category_id;


    public RoomReservationDelete() {

    }

    public RoomReservationDelete(Long hotel_id, Date date, String room_category_id) {
        this.hotel_id = hotel_id;
        this.date = date;
        this.room_category_id = room_category_id;
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

    public String getKeyForCache() {

        return hotel_id.toString() + "@" + date.toString() + "@" + room_category_id;

    }


}
