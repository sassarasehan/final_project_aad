package lk.ijse.final_project_aad.dto;

import lk.ijse.final_project_aad.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingViewDTO {
    private String bookingId;
    private String userEmail;
    private String status;
    private double amount;

    public BookingViewDTO(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.userEmail = booking.getUser().getEmail();
        this.status = booking.getStatus();
        this.amount = booking.getAmount();
    }

}
