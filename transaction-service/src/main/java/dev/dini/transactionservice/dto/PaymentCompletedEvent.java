package dev.dini.transactionservice.dto;

import dev.dini.transactionservice.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentRequestId,
        UUID customerId,
        BigDecimal amount,
        TransactionStatus status, // SUCCESS, FAILED
        LocalDateTime completedAt
) {}

