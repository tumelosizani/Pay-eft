package dev.dini.twofaservice.dto;

import dev.dini.twofaservice.entity.TwoFaStatus;
import java.util.UUID;

public record TwoFaResultEvent(
        UUID paymentRequestId,
        TwoFaStatus status
) { }