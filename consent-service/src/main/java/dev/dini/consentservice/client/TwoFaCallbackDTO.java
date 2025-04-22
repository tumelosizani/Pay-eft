package dev.dini.consentservice.client;

import lombok.Data;

import java.util.UUID;

@Data
public class TwoFaCallbackDTO {
    private UUID consentId;
    private TwoFaStatus status;
}
