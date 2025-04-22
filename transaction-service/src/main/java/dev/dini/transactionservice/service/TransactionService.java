package dev.dini.transactionservice.service;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;
import dev.dini.transactionservice.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    void processCompletedEftPayment(PaymentCompletedEvent event);
}
