package lk.ijse.final_project_aad.service;

import lk.ijse.final_project_aad.dto.BookingDTO;
import lk.ijse.final_project_aad.dto.BookingViewDTO;
import lk.ijse.final_project_aad.dto.PaymentDTO;
import lk.ijse.final_project_aad.entity.Booking;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);
    void confirmBooking(String bookingId, PaymentDTO paymentDTO);
    BookingDTO getBookingById(String bookingId);

    List<BookingViewDTO> getUserBookings(String email);
}