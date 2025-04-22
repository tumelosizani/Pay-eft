package dev.dini.gatewayservice.dto;

import java.math.BigDecimal;

public record PaymentCreateDTO(
        String identifier, // email / phone / idNumber
        BigDecimal amount,
        String recipientAccount
) { }
