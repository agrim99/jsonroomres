package io.project.mapjson.reqroomres.reqroomres.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomReservationDto {

    @Min(value = 1,message = "hotel_id should be greater than 0")
    @NotNull(message="Hotel Id is required")
    private Long hotelId;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    @Min(value = 1,message = "Room_Category_Id should be greater than 0")
    @NotNull(message="Room_Category_Id is required")
    private String roomCategoryId;

    @Min(value = 1,message = "Occupancy should be greater than 0")
    @Max(value = 3,message = "Occupancy should be less than 3")
    @NotNull(message="Occupancy cannot be null")
    private Integer occupancy;


    public RoomReservationDto() {

    }

    public RoomReservationDto(Long hotelId, Date date, String roomCategoryId, Integer occupancy) {
        this.hotelId = hotelId;
        this.date = date;
        this.roomCategoryId = roomCategoryId;
        this.occupancy = occupancy;
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

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public String getKeyForCache() {

        return hotelId.toString() + "@" + date.toString() + "@" + roomCategoryId;

    }


}
