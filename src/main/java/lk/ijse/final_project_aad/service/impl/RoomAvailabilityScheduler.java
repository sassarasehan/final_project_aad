package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.entity.Booking;
import lk.ijse.final_project_aad.entity.Room;
import lk.ijse.final_project_aad.repo.BookingRepository;
import lk.ijse.final_project_aad.repo.RoomRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomAvailabilityScheduler {

/*    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public RoomAvailabilityScheduler(BookingRepository bookingRepository, 
                                   RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    // Runs every day at 3 AM
    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void updateRoomAvailability() {
        LocalDateTime now = LocalDateTime.now();
        
        // Find all confirmed bookings where checkout has passed but room not yet marked available
        List<Booking> completedBookings = bookingRepository
                .findByStatusAndRoomMarkedAvailableAndCheckOutDateBefore(
                    "CONFIRMED", false, now);
        
        for (Booking booking : completedBookings) {
            Room room = booking.getRoom();
            room.setAvailable("available");
            roomRepository.save(room);
            
            booking.setResourceMarkedAvailable(true);
            bookingRepository.save(booking);
        }
    }*/
}