package lk.ijse.final_project_aad.dto;

import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaxiBookingDTO {
    private Long bookingId;
    private LocalDateTime pickupTime;
    private String pickupLocation;
    private String dropOffLocation;
    private String taxiId;
    private Long uid;
    private double fare;
}
