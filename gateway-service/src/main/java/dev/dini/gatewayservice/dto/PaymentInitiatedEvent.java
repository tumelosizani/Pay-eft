package dev.dini.gatewayservice.dto;

import java.util.UUID;

public record PaymentInitiatedEvent(
        UUID paymentRequestId,
        UUID customerId
) { }
