package dev.dini.twofaservice.dto;

import dev.dini.twofaservice.entity.TwoFaStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TwoFaCallbackDTO {
    private UUID consentId;
    private TwoFaStatus status;
}
