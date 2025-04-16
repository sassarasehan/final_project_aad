package lk.ijse.final_project_aad.service.impl;

import lk.ijse.final_project_aad.config.UserNotFoundException;
import lk.ijse.final_project_aad.dto.BookingDTO;
import lk.ijse.final_project_aad.dto.BookingViewDTO;
import lk.ijse.final_project_aad.dto.PaymentDTO;
import lk.ijse.final_project_aad.entity.*;
import lk.ijse.final_project_aad.repo.*;
import lk.ijse.final_project_aad.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ExpressionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final TaxiRepository taxiRepository;
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              PaymentRepository paymentRepository,
                              HotelRepository hotelRepository,
                              RoomRepository roomRepository,
                              TaxiRepository taxiRepository,
                              TourPackageRepository tourPackageRepository,
                              UserRepository userRepository,
                              ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.taxiRepository = taxiRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Validate user exists
        User user = userRepository.findByEmail(bookingDTO.getUserEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Create new booking
        Booking booking = new Booking();
        String lastId = bookingRepository.findLastBookingId();
        int nextId = 1;

        if (lastId != null && lastId.matches("B\\d+")) {
            nextId = Integer.parseInt(lastId.substring(1)) + 1;
        }

        String newId = String.format("B%03d", nextId);
        booking.setBookingId(newId);
        booking.setUser(user);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");
        booking.setBookingType(bookingDTO.getBookingType());
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setGuestCount(bookingDTO.getGuestCount());
        booking.setAmount(bookingDTO.getAmount()); // Make sure to set the amount


        // Handle different booking types
        switch (bookingDTO.getBookingType()) {
            case "HOTEL":
                Hotel hotel = hotelRepository.findById(bookingDTO.getHotelId())
                        .orElseThrow(() -> new ExpressionException("Hotel not found"));
                booking.setHotel(hotel);
                break;

            case "ROOM":
                Room room = roomRepository.findById(bookingDTO.getRoomId())
                        .orElseThrow(() -> new ExpressionException("Room not found"));

                // Check room availability
                if (!"available".equalsIgnoreCase(room.getAvailable())) {
                    throw new ExpressionException("Room is not available");
                }

                // Check for date conflicts
                boolean hasConflict = bookingRepository.existsByRoomAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        room,
                        "CONFIRMED",
                        bookingDTO.getEndDate(),
                        bookingDTO.getStartDate());

                if (hasConflict) {
                    throw new ExpressionException("Room is already booked for the selected dates");
                }

                booking.setRoom(room);
                break;

            case "TAXI":
                Taxi taxi = taxiRepository.findById(bookingDTO.getTaxiId())
                        .orElseThrow(() -> new ExpressionException("Taxi not found"));
                if (!"available".equalsIgnoreCase(taxi.getIsAvailable())) {
                    throw new ExpressionException("Taxi is not available");
                }
                booking.setTaxi(taxi);
                break;

            case "TOUR":
                TourPackage tourPackage = tourPackageRepository.findById(bookingDTO.getPackageId())
                        .orElseThrow(() -> new ExpressionException("Tour package not found"));
                booking.setTourPackage(tourPackage);
                break;

            default:
                throw new IllegalArgumentException("Invalid booking type");
        }

        // Save the booking
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    @Override
    public void confirmBooking(String bookingId, PaymentDTO paymentDTO) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ExpressionException("Booking not found"));

        // Validate booking can be confirmed
        if (!"PENDING".equals(booking.getStatus())) {
            throw new ExpressionException("Booking cannot be confirmed - invalid status");
        }

        // Create and save payment
        Payment payment = new Payment();
        payment.setPaymentId("PAY-" + UUID.randomUUID().toString().substring(0, 8));
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTransactionId(paymentDTO.getTransactionId());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBooking(booking);

        paymentRepository.save(payment);

        // Update booking status and link payment
        booking.setStatus("CONFIRMED");
        booking.setPayment(payment);
        bookingRepository.save(booking);

        // Mark resources as unavailable if applicable
        switch (booking.getBookingType()) {
            case "ROOM":
                Room room = booking.getRoom();
                room.setAvailable("unavailable");
                roomRepository.save(room);
                break;

            case "TAXI":
                Taxi taxi = booking.getTaxi();
                taxi.setIsAvailable("unavailable");
                taxiRepository.save(taxi);
                break;
        }
    }

    @Override
    public BookingDTO getBookingById(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ExpressionException("Booking not found"));
        return modelMapper.map(booking, BookingDTO.class);
    }

    public List<BookingViewDTO> getUserBookings(String email) {
        return bookingRepository.findByUserEmail(email)
                .stream()
                .map(BookingViewDTO::new)
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 3 * * ?") // Runs daily at 3 AM
    public void updateResourceAvailability() {
        LocalDateTime now = LocalDateTime.now();

        // Update rooms availability for completed bookings
        bookingRepository.findByStatusAndEndDateLessThanAndResourceMarkedAvailable("CONFIRMED", now, false)
                .forEach(booking -> {
                    if (booking.getRoom() != null) {
                        Room room = booking.getRoom();
                        room.setAvailable("available");
                        roomRepository.save(room);
                    }
                    if (booking.getTaxi() != null) {
                        Taxi taxi = booking.getTaxi();
                        taxi.setIsAvailable("available");
                        taxiRepository.save(taxi);
                    }
                    booking.setResourceMarkedAvailable(true);
                    bookingRepository.save(booking);
                });
    }
}