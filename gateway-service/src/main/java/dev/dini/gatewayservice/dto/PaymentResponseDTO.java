package dev.dini.gatewayservice.dto;

import dev.dini.gatewayservice.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime initiatedAt
) {
}
