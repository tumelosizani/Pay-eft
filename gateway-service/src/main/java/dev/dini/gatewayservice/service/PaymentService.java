package dev.dini.gatewayservice.service;

import dev.dini.gatewayservice.dto.PaymentCreateDTO;
import dev.dini.gatewayservice.dto.PaymentResponseDTO;
import dev.dini.gatewayservice.entity.PaymentStatus;

import java.util.UUID;

public interface PaymentService {
    PaymentResponseDTO initiatePayment (PaymentCreateDTO paymentCreateDTO);

    void updatePaymentStatus(UUID paymentRequestId, PaymentStatus status);

    void completePayment(UUID paymentRequestId);
}
