// PaymentService.java
package lk.ijse.final_project_aad.service;

import com.stripe.exception.StripeException;
import lk.ijse.final_project_aad.dto.PaymentRequestDTO;
import lk.ijse.final_project_aad.dto.PaymentResponseDTO;

import java.util.List;

// PaymentService.java
public interface PaymentService {
    PaymentResponseDTO createPaymentIntent(PaymentRequestDTO paymentRequestDTO) throws StripeException;
}
