package lk.ijse.final_project_aad.controller;

import lk.ijse.final_project_aad.dto.BookingDTO;
import lk.ijse.final_project_aad.dto.BookingViewDTO;
import lk.ijse.final_project_aad.dto.PaymentDTO;
import lk.ijse.final_project_aad.entity.Booking;
import lk.ijse.final_project_aad.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO));
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<Void> confirmBooking(
            @PathVariable String bookingId,
            @RequestBody PaymentDTO paymentDTO) {
        bookingService.confirmBooking(bookingId, paymentDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable String bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

 /*   @GetMapping("/user/{email}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getUserBookings(email));
    }*/

    @GetMapping("/user/{email}")
    public ResponseEntity<List<BookingViewDTO>> getUserBookings(@PathVariable String email) {
        return ResponseEntity.ok(bookingService.getUserBookings(email));
    }
}