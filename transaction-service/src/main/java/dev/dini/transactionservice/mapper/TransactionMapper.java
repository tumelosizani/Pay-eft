package dev.dini.transactionservice.mapper;

import dev.dini.transactionservice.dto.PaymentCompletedEvent;
import dev.dini.transactionservice.dto.TransactionResponseDTO;
import dev.dini.transactionservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction toEntity(PaymentCompletedEvent event);

    TransactionResponseDTO toResponseDTO(Transaction transaction);
}
