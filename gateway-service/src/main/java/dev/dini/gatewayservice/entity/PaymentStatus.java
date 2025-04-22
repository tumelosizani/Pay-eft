package dev.dini.gatewayservice.entity;

public enum PaymentStatus {
    INITIATED,
    PENDING,
    APPROVED,
    REJECTED,
    FAILED,
    COMPLETED,
    PENDING_2FA
}
