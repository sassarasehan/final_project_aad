package lk.ijse.final_project_aad.controller;

import com.stripe.exception.StripeException;
import lk.ijse.final_project_aad.dto.PaymentRequestDTO;
import lk.ijse.final_project_aad.dto.PaymentResponseDTO;
import lk.ijse.final_project_aad.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentResponseDTO> createPaymentIntent(@RequestBody PaymentRequestDTO dto) throws StripeException, StripeException {
        return ResponseEntity.ok(paymentService.createPaymentIntent(dto));
    }

}