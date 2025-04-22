package dev.dini.transactionservice.service;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;
import dev.dini.transactionservice.entity.Transaction;
import dev.dini.transactionservice.entity.TransactionStatus;
import dev.dini.transactionservice.entity.TransactionType;
import dev.dini.transactionservice.mapper.TransactionMapper;
import dev.dini.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public void processCompletedEftPayment(PaymentCompletedEvent event) {
        Transaction txn = transactionMapper.toEntity(event);
        txn.setType(TransactionType.EFT);
        txn.setCreatedAt(LocalDateTime.now());

        txn.setStatus(TransactionStatus.SUCCESS);
        txn.setCompletedAt(LocalDateTime.now());

        transactionRepository.save(txn);
    }



    @Override
    public void recordTransaction(PaymentCompletedEvent event) {
        Transaction txn = transactionMapper.toEntity(event);
        transactionRepository.save(txn);
    }

    @Override
    public List<Transaction> getTransactionsByCustomer(UUID customerId) {
        return transactionRepository.findByCustomerId(customerId);
    }
}
