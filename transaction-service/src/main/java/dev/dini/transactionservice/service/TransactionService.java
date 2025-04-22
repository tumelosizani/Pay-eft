package dev.dini.transactionservice.service;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;

public interface TransactionService {
    void processCompletedEftPayment(PaymentCompletedEvent event);
}
