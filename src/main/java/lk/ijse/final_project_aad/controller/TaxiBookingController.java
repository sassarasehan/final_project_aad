package lk.ijse.final_project_aad.controller;


import lk.ijse.final_project_aad.dto.TaxiBookingDTO;
import lk.ijse.final_project_aad.service.TaxiBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/taxi-bookings")
@CrossOrigin
public class TaxiBookingController {
    private final TaxiBookingService taxiBookingService;

    public TaxiBookingController(TaxiBookingService taxiBookingService) {
        this.taxiBookingService = taxiBookingService;
    }

    @GetMapping("getAll")
    public List<TaxiBookingDTO> getAllBookings() {
        return taxiBookingService.getAllBookings();
    }

    @GetMapping("getBooTaxiById/{bookingId}")
    public ResponseEntity<TaxiBookingDTO> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok(taxiBookingService.getBookingById(bookingId));
    }

    @PostMapping("/bookTaxi")
    public ResponseEntity<TaxiBookingDTO> bookTaxi(@RequestBody TaxiBookingDTO bookingDTO) {
        return ResponseEntity.ok(taxiBookingService.bookTaxi(bookingDTO));
    }

    @DeleteMapping("delete/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable ("bookingId") Long bookingId) {
        taxiBookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
