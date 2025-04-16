package lk.ijse.final_project_aad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "bookingId")
    private Booking booking;

    private double amount;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String status; // "PENDING", "COMPLETED", "FAILED"
}