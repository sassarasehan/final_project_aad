/*
package lk.ijse.final_project_aad.service.impl;


import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.entity.Hotel;
import lk.ijse.final_project_aad.entity.Room;
import lk.ijse.final_project_aad.repo.RoomRepository;
import lk.ijse.final_project_aad.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(room -> new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.isAvailable(), room.getHotel().getHotelId()))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(String.valueOf(roomId))
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.isAvailable(), room.getHotel().getHotelId());
    }

    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO) {
        Room room = new Room();
        room.setRoomId(room.getRoomId());
        room.setRoomType(roomDTO.getRoomType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        // assuming hotel exists already
        room.setHotel(new Hotel(roomDTO.getHotelId()));
        roomRepository.save(room);
        return new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.isAvailable(), room.getHotel().getHotelId());
    }

    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO) {
        Room room = roomRepository.findById(String.valueOf(roomId))
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(roomDTO.getRoomType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setHotel(new Hotel(roomDTO.getHotelId()));
        roomRepository.save(room);

        return new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.isAvailable(), room.getHotel().getHotelId());
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(String.valueOf(roomId));
    }
}
*/
package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.entity.Hotel;
import lk.ijse.final_project_aad.entity.Room;
import lk.ijse.final_project_aad.repo.RoomRepository;
import lk.ijse.final_project_aad.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public String generateNextRoomId() {
        String lastId = roomRepository.findLastRoomId();
        if (lastId == null) {
            return "R001";
        }
        int nextIdNum = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("R%03d", nextIdNum);
    }

    public long countAvailableRooms() {
        return roomRepository.countByAvailable("available");
    }

    @Override
    public List<Room> getRoomsByHotelId(String hotelId) {
        return roomRepository.findByHotelHotelId(hotelId);
    }

    // Get all rooms
    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDTO(
                        room.getRoomId(),
                        room.getRoomType(),
                        room.getPrice(),
                        room.getAvailable(),
                        room.getHotel().getHotelId(),
                        room.getImage()
                )).collect(Collectors.toList());
    }

    // Get room by ID
    @Override
    public RoomDTO getRoomById(String roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return new RoomDTO(
                room.getRoomId(),
                room.getRoomType(),
                room.getPrice(),
                room.getAvailable(),
                room.getHotel().getHotelId(),
                room.getImage()
        );
    }

    // Save room with image
    @Override
    public RoomDTO saveRoom(RoomDTO roomDTO, MultipartFile file) {
        Room room = new Room();
        room.setRoomId(generateNextRoomId());
        room.setRoomType(roomDTO.getRoomType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.getAvailable());
        room.setHotel(new Hotel(roomDTO.getHotelId()));

        // Save image
        String imagePath = saveImage(file);
        room.setImage(imagePath);

        roomRepository.save(room);
        return new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.getAvailable(), room.getHotel().getHotelId(), room.getImage());
    }

    // Update room with new image
    @Override
    public RoomDTO updateRoom(String roomId, RoomDTO roomDTO, MultipartFile file) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setRoomType(roomDTO.getRoomType());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.getAvailable());
        room.setHotel(new Hotel(roomDTO.getHotelId()));

        if (file != null && !file.isEmpty()) {
            String imagePath = saveImage(file);
            room.setImage(imagePath);
        }

        roomRepository.save(room);
        return new RoomDTO(room.getRoomId(), room.getRoomType(), room.getPrice(), room.getAvailable(), room.getHotel().getHotelId(), room.getImage());
    }

    // Delete room
    @Override
    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }

    private String saveImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = "C:\\Users\\Sehan\\IdeaProjects\\JAVAEE\\final_project_aad\\src\\main\\resources\\static\\uploads";
        File directory = new File(uploadDir);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create upload directory");
            }
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + "/" + fileName;

        try {
            File saveFile = new File(filePath);
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

// Save only the relative path
        return "/uploads/" + fileName;

    }
    public List<Room> getRoomsByHotelIdAndAvailability(String hotelId, String available) {
        return roomRepository.findByHotel_HotelIdAndAvailable(hotelId, available);
    }

}
