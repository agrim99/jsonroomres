package io.project.mapjson.reqroomres.reqroomres.repository;

import io.project.mapjson.reqroomres.reqroomres.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {


    @Transactional
    @Query(value = "SELECT CAST(occupancy_to_price as varchar) FROM room_reservations WHERE hotel_id=:hotelId AND date=:date AND room_category_id=:roomCategoryId", nativeQuery = true)
    List<String> getPrice(Long hotelId, Date date, String roomCategoryId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE room_reservations SET occupancy_to_price=:occupancyToPrice WHERE hotel_id=:hotelId AND date=:date AND room_category_id=:roomCategoryId", nativeQuery = true)
    void updatePrice(Long hotelId, Date date, String roomCategoryId, Map<String, Double> occupancyToPrice);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM room_reservations WHERE hotel_id=:hotelId AND date=:date AND room_category_id=:roomCategoryId", nativeQuery = true)
    void deleterow(Long hotelId, Date date, String roomCategoryId);


}
