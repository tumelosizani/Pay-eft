package dev.dini.gatewayservice.dto;

import lombok.Data;

import java.math.BigDecimal;

public record PaymentCreateDTO(
        String identifier, // email / phone / idNumber
        BigDecimal amount,
        String recipientAccount
) { }
