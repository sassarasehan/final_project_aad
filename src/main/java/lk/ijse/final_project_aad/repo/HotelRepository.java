package lk.ijse.final_project_aad.repo;



import lk.ijse.final_project_aad.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    Optional<Hotel> findByName(String name);
    Optional<Hotel> findTopByOrderByHotelIdDesc();
    List<Hotel> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(String name, String address);
    long count();

}