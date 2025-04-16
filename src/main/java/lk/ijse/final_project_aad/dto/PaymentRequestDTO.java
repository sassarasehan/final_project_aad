package lk.ijse.final_project_aad.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @Min(value = 1, message = "Amount must be at least 1")
    private double amount;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;

    @NotBlank(message = "Receipt email is required")
    private String receiptEmail;
}