package dev.dini.consentservice.dto;

import dev.dini.consentservice.entity.ConsentStatus;
import dev.dini.consentservice.entity.ConsentType;
import lombok.Data;

import java.util.UUID;

@Data
public class ConsentRequestDTO {
    private UUID paymentRequestId;
    private UUID userId;
    private ConsentType type;
    private ConsentStatus status;

}
