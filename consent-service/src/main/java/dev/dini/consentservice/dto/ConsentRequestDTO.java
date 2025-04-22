package dev.dini.consentservice.dto;

import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.entity.ConsentType;

import java.util.UUID;

public record ConsentRequestDTO(
        UUID userId,
        UUID paymentRequestId,
        ConsentType type,
        ConsentStatus status
) {

}
