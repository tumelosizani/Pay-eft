package dev.dini.consentservice.dto;

import java.util.UUID;

public record PaymentInitiatedEvent(
        UUID paymentRequestId,
        UUID customerId
) {}
