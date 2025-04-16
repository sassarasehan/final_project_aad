/*
package lk.ijse.final_project_aad.controller;


import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/get")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("getRooID/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @PostMapping("/save")
    public ResponseEntity<RoomDTO> saveRoom(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.saveRoom(roomDTO));
    }

    @PutMapping("update/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long roomId, @RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, roomDTO));
    }

    @DeleteMapping("delete/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable ("roomId") Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }
}
*/
package lk.ijse.final_project_aad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.final_project_aad.dto.CountDTO;
import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.entity.Room;
import lk.ijse.final_project_aad.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin(origins = "*")
public class RoomController {
    private final RoomService roomService;
    private final ObjectMapper objectMapper;

    public RoomController(RoomService roomService, ObjectMapper objectMapper) {
        this.roomService = roomService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/get")
    public List<RoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/nextRoomId")
    public ResponseEntity<String> getNextRoomId() {
        return ResponseEntity.ok(roomService.generateNextRoomId());
    }

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RoomDTO> saveRoom(@RequestPart("room") String roomJson, @RequestPart("image") MultipartFile file) {
        try {
            RoomDTO roomDTO = objectMapper.readValue(roomJson, RoomDTO.class);
            return ResponseEntity.ok(roomService.saveRoom(roomDTO, file));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage());
        }
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable String roomId, @RequestPart("room") String roomJson, @RequestPart(value = "image", required = false) MultipartFile file) {
        try {
            RoomDTO roomDTO = objectMapper.readValue(roomJson, RoomDTO.class);
            return ResponseEntity.ok(roomService.updateRoom(roomId, roomDTO, file));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getRooID/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable String roomId) {
        try {
            RoomDTO roomDTO = roomService.getRoomById(roomId);
            return ResponseEntity.ok(roomDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> getAvailableRoomCount() {
        return ResponseEntity.ok(new CountDTO(roomService.countAvailableRooms()));
    }

    @GetMapping("/rooms/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotelAndAvailability(
            @PathVariable String hotelId,
            @RequestParam(required = false) String available) {

        List<Room> rooms;

        if (available != null && !available.isEmpty()) {
            rooms = roomService.getRoomsByHotelIdAndAvailability(hotelId, available);
        } else {
            rooms = roomService.getRoomsByHotelId(hotelId);
        }

        if (rooms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(rooms);
    }

}
