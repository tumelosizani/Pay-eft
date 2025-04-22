package dev.dini.twofaservice.dto;

import dev.dini.twofaservice.entity.TwoFaStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TwoFaResponseDTO(
        UUID id,
        UUID customerId,
        UUID paymentRequestId,
        UUID consentId,
        TwoFaStatus status,
        LocalDateTime updatedAt
) {
}
