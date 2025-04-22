package dev.dini.twofaservice.dto;

import java.util.UUID;


public record TwoFaCreateDTO(
        UUID customerId,
        UUID paymentRequestId,
        UUID consentId

) {
}
