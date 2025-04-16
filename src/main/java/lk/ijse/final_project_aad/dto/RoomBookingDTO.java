package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingDTO {
    private String bookingId;
    private String userEmail;
    private String roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status; // PENDING, PAID
}
