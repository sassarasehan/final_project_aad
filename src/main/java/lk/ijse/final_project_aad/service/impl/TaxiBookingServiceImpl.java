package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.dto.TaxiBookingDTO;
import lk.ijse.final_project_aad.entity.Taxi;
import lk.ijse.final_project_aad.entity.TaxiBooking;
import lk.ijse.final_project_aad.entity.User;
import lk.ijse.final_project_aad.repo.TaxiBookingRepository;
import lk.ijse.final_project_aad.repo.TaxiRepository;
import lk.ijse.final_project_aad.repo.UserRepository;
import lk.ijse.final_project_aad.service.TaxiBookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaxiBookingServiceImpl implements TaxiBookingService {

    private final TaxiBookingRepository taxiBookingRepository;
    private final TaxiRepository taxiRepository;
    private final UserRepository userRepository;

    public TaxiBookingServiceImpl(TaxiBookingRepository taxiBookingRepository, TaxiRepository taxiRepository, UserRepository userRepository) {
        this.taxiBookingRepository = taxiBookingRepository;
        this.taxiRepository = taxiRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaxiBookingDTO> getAllBookings() {
        return taxiBookingRepository.findAll()
                .stream()
                .map(booking -> new TaxiBookingDTO(booking.getTaxiBookingId(), booking.getPickupTime(), booking.getPickupLocation(), booking.getDropOffLocation(),
                        booking.getTaxi().getTaxiId(), booking.getUser().getUid(), booking.getFare()))
                .collect(Collectors.toList());
    }

    @Override
    public TaxiBookingDTO getBookingById(Long bookingId) {
        TaxiBooking booking = taxiBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Taxi booking not found"));
        return new TaxiBookingDTO(booking.getTaxiBookingId(), booking.getPickupTime(), booking.getPickupLocation(), booking.getDropOffLocation(),
                booking.getTaxi().getTaxiId(), booking.getUser().getUid(), booking.getFare());
    }

    @Override
    public TaxiBookingDTO bookTaxi(TaxiBookingDTO bookingDTO) {
        Taxi taxi = taxiRepository.findById(bookingDTO.getTaxiId())
                .orElseThrow(() -> new RuntimeException("Taxi not found"));
        User user = userRepository.findById(String.valueOf(bookingDTO.getUid()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        TaxiBooking booking = new TaxiBooking();
        booking.setTaxiBookingId(booking.getTaxiBookingId());
        booking.setPickupTime(bookingDTO.getPickupTime());
        booking.setPickupLocation(bookingDTO.getPickupLocation());
        booking.setDropOffLocation(bookingDTO.getDropOffLocation());
        booking.setTaxi(taxi);
        booking.setUser(user);

        // Set fare (simple example for calculation)
        booking.setFare(10.0); // You can calculate based on distance, time, etc.

        taxiBookingRepository.save(booking);
        return new TaxiBookingDTO(booking.getTaxiBookingId(), booking.getPickupTime(), booking.getPickupLocation(), booking.getDropOffLocation(),
                booking.getTaxi().getTaxiId(), booking.getUser().getUid(), booking.getFare());
    }

    @Override
    public void cancelBooking(Long bookingId) {
        TaxiBooking booking = taxiBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        taxiBookingRepository.delete(booking);
    }
}
