package io.project.mapjson.roomres.repository;

import io.project.mapjson.roomres.model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {


    //@Query(value = "INSERT INTO room_reservations (hotel_id, date, occupancy_to_price, room_category_id) VALUES (:hotel_id, :date, :occupancy_to_price, :room_category_id)", nativeQuery = true)
    //void savePrice(Long hotel_id, String date, String occupancy_to_price, String room_category_id);



    @Query(value = "SELECT CAST(occupancy_to_price as varchar) FROM room_reservations WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
    List<String> getPrice(Long hotel_id, String date, String room_category_id);



//    @Query(value = "UPDATE room_reservations SET occupancy_to_price=:occupancy_to_price WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
//    void updatePrice(Long hotel_id, String date, String room_category_id, Map<String, Double> occupancy_to_price);
//
//
//    @Query(value = "DELETE FROM room_reservations WHERE hotel_id=:hotel_id AND date=:date AND room_category_id=:room_category_id", nativeQuery = true)
//    void deleterow(Long hotel_id, String date, String room_category_id);


}
