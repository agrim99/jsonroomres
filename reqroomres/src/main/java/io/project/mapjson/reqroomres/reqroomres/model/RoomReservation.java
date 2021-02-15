package io.project.mapjson.reqroomres.reqroomres.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.project.mapjson.reqroomres.reqroomres.converter.StringMapConverter;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "room_reservations")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1,message = "hotel_id should be greater than 0")
    @NotNull(message ="Hotel Id is required")
    @Column(name = "hotel_id")
    private Long hotelId;


    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Date date;

    @Min(value = 1,message = "Room_Category_Id should be greater than 0")
    @NotNull(message="Room_Category_Id is required")
    @Column(name = "date")
    @Column(name = "room_category_id")
    private String roomCategoryId;

    @Column(name = "occupancy_to_price", columnDefinition = "JSON")
    @Convert(converter = StringMapConverter.class)
    private Map<String, Double> occupancyToPrice;



    public RoomReservation() {

    }

    public RoomReservation(Long hotelId, Date date, String roomCategoryId, Map<String, Double> occupancyToPrice) {
        this.hotelId = hotelId;
        this.date = date;
        this.roomCategoryId = roomCategoryId;
        this.occupancyToPrice = occupancyToPrice;
    }

    public RoomReservation(Long id, Long hotelId, Date date, String roomCategoryId, Map<String, Double> occupancyToPrice) {
        this.id = id;
        this.hotelId = hotelId;
        this.date = date;
        this.roomCategoryId = roomCategoryId;
        this.occupancyToPrice = occupancyToPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Map<String, Double> getOccupancyToPrice() {
        return occupancyToPrice;
    }

    public void setOccupancyToPrice(Map<String, Double> occupancyToPrice) {
        this.occupancyToPrice = occupancyToPrice;
    }

    public String getKeyForCache() {

        return hotelId.toString() + "@" + date.toString() + "@" + roomCategoryId;

    }


}