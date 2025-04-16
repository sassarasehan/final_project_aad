package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String bookingId;
    private String userEmail;
    private String bookingType; // "HOTEL", "ROOM", "TAXI", "TOUR"

    private String hotelId;
    private String roomId;
    private String taxiId;
    private String packageId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int guestCount;
    private String status;
    private double amount;
}