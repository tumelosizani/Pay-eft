package dev.dini.transactionservice.service;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;
import dev.dini.transactionservice.entity.*;
import dev.dini.transactionservice.mapper.TransactionMapper;
import dev.dini.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public void processCompletedEftPayment(PaymentCompletedEvent event) {
        log.info("Received Payment Completed Event: {}", event);
        Transaction txn = transactionMapper.toEntity(event);
        txn.setType(TransactionType.EFT);
        txn.setCreatedAt(LocalDateTime.now());

        txn.setStatus(TransactionStatus.SUCCESS);
        txn.setCompletedAt(LocalDateTime.now());

        log.info("Saving transaction: {}", txn);
        transactionRepository.save(txn);
    }


}
