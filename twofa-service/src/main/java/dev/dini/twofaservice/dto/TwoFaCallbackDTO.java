package dev.dini.twofaservice.dto;

import dev.dini.twofaservice.entity.TwoFaStatus;

import java.util.UUID;

public record TwoFaCallbackDTO(
        UUID consentId,
        TwoFaStatus status
){}
