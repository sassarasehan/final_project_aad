package lk.ijse.final_project_aad.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lk.ijse.final_project_aad.dto.PaymentRequestDTO;
import lk.ijse.final_project_aad.dto.PaymentResponseDTO;
import lk.ijse.final_project_aad.repo.PaymentRepository;
import lk.ijse.final_project_aad.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    @Override
    public PaymentResponseDTO createPaymentIntent(PaymentRequestDTO paymentRequestDTO) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (paymentRequestDTO.getAmount() * 1)) // Convert to cents
                .setCurrency(paymentRequestDTO.getCurrency().toLowerCase())
                .putMetadata("integration_check", "accept_a_payment")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        return new PaymentResponseDTO(
                paymentIntent.getClientSecret(),
                paymentIntent.getAmount(),
                paymentIntent.getCurrency(),
                paymentIntent.getStatus()
        );
    }


}