package lk.ijse.final_project_aad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String clientSecret;
    private Long amount;
    private String currency;
    private String status;
}
