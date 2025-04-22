package dev.dini.consentservice.dto;

import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.entity.ConsentType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsentResponseDTO(
        UUID id,
        UUID paymentRequestId,
        UUID userId,
        ConsentStatus status,
        ConsentType type,
        LocalDateTime consentedAt
) {
}
