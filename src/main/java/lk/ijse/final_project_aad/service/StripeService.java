package lk.ijse.final_project_aad.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface StripeService {
    PaymentIntent createPaymentIntent(double amount, String currency) throws StripeException;
}