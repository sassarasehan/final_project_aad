package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.HotelDTO;
import lk.ijse.final_project_aad.entity.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getAllHotels();
    String generateNextHotelId();
    HotelDTO getHotelByName(String name);
    HotelDTO saveHotel(HotelDTO hotelDTO, MultipartFile file);
    HotelDTO updateHotelById(String id, HotelDTO hotelDTO, MultipartFile file);
    void deleteHotelById(String id);
    List<HotelDTO> searchHotels(String query);

    Hotel findHotelById(String hotelId);

    long countHotels();
}