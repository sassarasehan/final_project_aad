package lk.ijse.final_project_aad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.final_project_aad.dto.CountDTO;
import lk.ijse.final_project_aad.dto.HotelDTO;
import lk.ijse.final_project_aad.dto.RoomDTO;
import lk.ijse.final_project_aad.entity.Hotel;
import lk.ijse.final_project_aad.service.HotelService;
import lk.ijse.final_project_aad.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static lk.ijse.final_project_aad.service.impl.HotelServiceImpl.IMAGE_DIRECTORY;

@RestController
@RequestMapping("api/v1/hotels")
@CrossOrigin(origins = "*")
public class HotelController {
    private final HotelService hotelService;
    private final ObjectMapper objectMapper;

    public HotelController(HotelService hotelService, ObjectMapper objectMapper) {
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }


    @GetMapping("/get")
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }


    @GetMapping("/nextHotelId")
    public ResponseEntity<String> getNextHotelId() {
        return ResponseEntity.ok(hotelService.generateNextHotelId());
    }


    @GetMapping("/getByName/{name}")
    public ResponseEntity<HotelDTO> getHotelByName(@PathVariable String name) {
        try {
            HotelDTO hotelDTO = hotelService.getHotelByName(name);
            return ResponseEntity.ok(hotelDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/getById/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        Hotel hotel = hotelService.findHotelById(hotelId);
        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(hotel);
    }


    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HotelDTO> saveHotel(
            @RequestPart("hotel") String hotelJson,
            @RequestPart("image") MultipartFile file) {
        try {
            HotelDTO hotelDTO = objectMapper.readValue(hotelJson, HotelDTO.class);
            return ResponseEntity.ok(hotelService.saveHotel(hotelDTO, file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Send appropriate error response
        }
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HotelDTO> updateHotel(
            @PathVariable String id,
            @RequestPart("hotel") String hotelJson,
            @RequestPart(value = "image", required = false) MultipartFile file) {
        try {
            HotelDTO hotelDTO = objectMapper.readValue(hotelJson, HotelDTO.class);
            return ResponseEntity.ok(hotelService.updateHotelById(id, hotelDTO, file));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Send appropriate error response
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String id) {
        hotelService.deleteHotelById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchHotels(@RequestParam String query) {
        List<HotelDTO> hotels = hotelService.searchHotels(query);
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(IMAGE_DIRECTORY, imageName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // or MediaType.IMAGE_PNG
                .body(imageBytes);
    }

    @GetMapping("/count")
    public ResponseEntity<CountDTO> getHotelCount() {
        return ResponseEntity.ok(new CountDTO(hotelService.countHotels()));
    }
}
