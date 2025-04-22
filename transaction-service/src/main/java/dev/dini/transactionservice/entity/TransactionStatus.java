package dev.dini.transactionservice.entity;

public enum TransactionStatus {
    SUCCESS,
    FAILED,
    PENDING,
    CANCELLED,
    REFUNDED,
    CHARGEBACK,
}
