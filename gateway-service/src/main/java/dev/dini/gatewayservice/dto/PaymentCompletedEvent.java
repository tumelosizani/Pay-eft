package dev.dini.gatewayservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentRequestId,
        UUID customerId,
        BigDecimal amount
) {

}
