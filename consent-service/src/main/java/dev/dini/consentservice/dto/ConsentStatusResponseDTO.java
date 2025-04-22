package dev.dini.consentservice.dto;

import dev.dini.consentservice.entity.ConsentStatus;

public record ConsentStatusResponseDTO(
        ConsentStatus status
) {
}
