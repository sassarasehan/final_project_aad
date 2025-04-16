/*
package lk.ijse.final_project_aad.service;


import lk.ijse.final_project_aad.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(Long roomId);
    RoomDTO saveRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);
    void deleteRoom(Long roomId);
}
*/
package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.entity.Room;
import lk.ijse.final_project_aad.repo.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDTO> getAllRooms();
    RoomDTO getRoomById(String roomId);
    RoomDTO saveRoom(RoomDTO roomDTO, MultipartFile file);
    RoomDTO updateRoom(String roomId, RoomDTO roomDTO, MultipartFile file);
    void deleteRoom(String roomId);
    String generateNextRoomId();

    long countAvailableRooms();

    List<Room> getRoomsByHotelId(String hotelId);

    List<Room> getRoomsByHotelIdAndAvailability(String hotelId, String available);
}
