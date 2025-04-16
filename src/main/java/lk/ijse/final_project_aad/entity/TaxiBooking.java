package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxiBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taxiBookingId;
    private LocalDateTime pickupTime;
    private String pickupLocation;
    private String dropOffLocation;
    @ManyToOne
    private Taxi taxi;
    @ManyToOne
    private User user;
    private double fare;
}
