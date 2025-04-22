package dev.dini.gatewayservice.dto;

import dev.dini.gatewayservice.entity.PaymentStatus;

import java.util.UUID;

public record TwoFaResultEvent(
        UUID paymentRequestId,
        PaymentStatus status,
        String message

) { }