package dev.dini.consentservice.client;

import java.util.UUID;

public record TwoFaRequestDTO(
        UUID customerId,
        UUID paymentRequestId,
        UUID consentId
) {

}