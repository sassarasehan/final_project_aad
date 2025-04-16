package lk.ijse.final_project_aad.service;


import lk.ijse.final_project_aad.dto.TaxiBookingDTO;

import java.util.List;

public interface TaxiBookingService {
    List<TaxiBookingDTO> getAllBookings();
    TaxiBookingDTO getBookingById(Long bookingId);
    TaxiBookingDTO bookTaxi(TaxiBookingDTO bookingDTO); // New taxi booking
    void cancelBooking(Long bookingId); // Cancel taxi booking
}
