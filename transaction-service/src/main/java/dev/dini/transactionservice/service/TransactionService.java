package dev.dini.transactionservice.service;

import dev.dini.common.events.PaymentCompletedEvent;

public interface TransactionService {
    void processCompletedEftPayment(PaymentCompletedEvent event);
}
