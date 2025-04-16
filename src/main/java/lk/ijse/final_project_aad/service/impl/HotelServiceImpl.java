package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.HotelDTO;
import lk.ijse.final_project_aad.entity.Hotel;
import lk.ijse.final_project_aad.repo.HotelRepository;

import lk.ijse.final_project_aad.service.HotelService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.IdGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;


    public static final String IMAGE_DIRECTORY = "uploads/images";

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;


        try {
            Files.createDirectories(Paths.get(IMAGE_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextHotelId() {
        String lastId = hotelRepository.findTopByOrderByHotelIdDesc()
                .map(Hotel::getHotelId)
                .orElse(null);

        if (lastId == null) {
            return "H001";
        }

        String lastNumericPart = lastId.substring(2);
        int lastNumber = Integer.parseInt(lastNumericPart);

        int nextNumber = lastNumber + 1;

        return String.format("H%03d", nextNumber);
    }


    @Override
    public HotelDTO getHotelByName(String name) {
        Hotel hotel = hotelRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Hotel not found with name: " + name));
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO saveHotel(HotelDTO hotelDTO, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String imagePath = saveImage(file);
                hotelDTO.setImage(imagePath);
            }

            Hotel hotel = modelMapper.map(hotelDTO, Hotel.class);
            hotel.setHotelId(generateNextHotelId());
            Hotel savedHotel = hotelRepository.save(hotel);
            return modelMapper.map(savedHotel, HotelDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save hotel: " + e.getMessage(), e);
        }
    }

    @Override
    public HotelDTO updateHotelById(String id, HotelDTO hotelDTO, MultipartFile file) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        try {
            if (file != null && !file.isEmpty()) {
                if (existingHotel.getImage() != null) {
                    deleteImage(existingHotel.getImage());
                }
                String imagePath = saveImage(file);
                hotelDTO.setImage(imagePath);
            } else {
                hotelDTO.setImage(existingHotel.getImage());
            }

            hotelDTO.setHotelId(id);
            Hotel updatedHotel = modelMapper.map(hotelDTO, Hotel.class);
            Hotel savedHotel = hotelRepository.save(updatedHotel);
            return modelMapper.map(savedHotel, HotelDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update hotel: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteHotelById(String id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        if (hotel.getImage() != null) {
            deleteImage(hotel.getImage());
        }

        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelDTO> searchHotels(String query) {
        List<Hotel> hotels = hotelRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(query, query);
        return hotels.stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Hotel findHotelById(String hotelId) {
        // Find hotel by its ID, if not found return null or throw an exception
        return hotelRepository.findById(hotelId).orElse(null);
    }

    @Override
    public long countHotels() {
        return hotelRepository.count();
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(IMAGE_DIRECTORY, fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    private void deleteImage(String imagePath) {
        try {
            Path filePath = Paths.get(IMAGE_DIRECTORY, imagePath);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image: " + imagePath, e);
        }
    }
}