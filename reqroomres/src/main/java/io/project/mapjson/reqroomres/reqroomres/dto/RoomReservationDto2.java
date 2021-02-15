package io.project.mapjson.reqroomres.reqroomres.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RoomReservationDto2 {

    @Min(value = 1,message = "hotel_id should be greater than 0")
    @NotNull(message="Hotel Id is required")
    private Long hotelId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    @Min(value = 1,message = "Room_Category_Id should be greater than 0")
    @NotNull(message="Room_Category_Id is required")
    @Column(name="room_category_id")
    private String roomCategoryId;


    public RoomReservationDto2() {

    }

    public RoomReservationDto2(Long hotelId, Date date, String roomCategoryId) {
        this.hotelId = hotelId;
        this.date = date;
        this.roomCategoryId = roomCategoryId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(String roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public String getKeyForCache() {

        return hotelId.toString() + "@" + date.toString() + "@" + roomCategoryId;

    }



}
