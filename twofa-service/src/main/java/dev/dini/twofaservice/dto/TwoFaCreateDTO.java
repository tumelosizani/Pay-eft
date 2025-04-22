package dev.dini.twofaservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TwoFaCreateDTO {
    private UUID customerId;
    private UUID paymentRequestId;
    private UUID consentId;
}
