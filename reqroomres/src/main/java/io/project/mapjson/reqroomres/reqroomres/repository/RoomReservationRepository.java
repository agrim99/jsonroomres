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
    @Query(value = "SELECT CAST(occupancy_to_price as varchar) FROM room_reservations WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
    List<String> getPrice(Long hotel_id, Date date, String room_category_id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE room_reservations SET occupancy_to_price=:occupancy_to_price WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
    void updatePrice(Long hotel_id, Date date, String room_category_id, Map<String, Double> occupancy_to_price);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM room_reservations WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
    void deleterow(Long hotel_id, Date date, String room_category_id);


}
