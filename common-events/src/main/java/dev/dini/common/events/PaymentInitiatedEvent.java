package dev.dini.common.events;

import java.util.UUID;

public record PaymentInitiatedEvent(
        UUID paymentRequestId,
        UUID customerId
) { }
