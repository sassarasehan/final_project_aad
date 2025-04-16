/*
package lk.ijse.final_project_aad.repo;


import lk.ijse.final_project_aad.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, String> {
    void deleteByHotel_HotelId(String hotelId);
}
*/
package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    @Query("SELECT r.roomId FROM Room r ORDER BY r.roomId DESC LIMIT 1")
    String findLastRoomId();



    long countByAvailable(String available);

    List<Room> findByHotel_HotelId(String hotelId);

    List<Room> findByAvailable(String available);

    List<Room> findByHotelHotelId(String hotelId);

    List<Room> findByHotel_HotelIdAndAvailable(String hotelId, String available);
}
