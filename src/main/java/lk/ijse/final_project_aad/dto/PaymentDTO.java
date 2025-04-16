package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private String paymentId;
    private String bookingId;
    private double amount;
    private String paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDate;
    private String status;
}