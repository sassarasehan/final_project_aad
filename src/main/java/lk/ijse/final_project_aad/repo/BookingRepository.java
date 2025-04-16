package lk.ijse.final_project_aad.repo;

import lk.ijse.final_project_aad.entity.Booking;
import lk.ijse.final_project_aad.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query("SELECT b FROM Booking b WHERE b.bookingType = 'ROOM' " +
            "AND b.status = 'CONFIRMED' " +
            "AND b.endDate < :currentDate " +
            "AND b.resourceMarkedAvailable = false")
    List<Booking> findCompletedRoomBookings(LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.bookingType = 'TAXI' " +
            "AND b.status = 'CONFIRMED' " +
            "AND b.endDate < :currentDate " +
            "AND b.resourceMarkedAvailable = false")
    List<Booking> findCompletedTaxiBookings(LocalDateTime currentDate);

    List<Booking> findByUserEmail(String userEmail);

/*
    List<Booking> findByStatusAndRoomMarkedAvailableAndCheckOutDateBefore(String confirmed, boolean b, LocalDateTime now);
*/
boolean existsByRoomAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        Room room, String status, LocalDateTime endDate, LocalDateTime startDate);

    List<Booking> findByStatusAndEndDateLessThanAndResourceMarkedAvailable(
            String status, LocalDateTime date, boolean markedAvailable);

    @Query(value = "SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1", nativeQuery = true)
    String findLastBookingId();
}