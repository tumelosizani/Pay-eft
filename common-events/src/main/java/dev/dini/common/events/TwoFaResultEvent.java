package dev.dini.common.events;

import java.util.UUID;

public record TwoFaResultEvent(
        UUID paymentRequestId,
        TwoFaStatus status
) { }
