package dev.dini.transactionservice.mapper;

import dev.dini.common.events.PaymentCompletedEvent;
import dev.dini.transactionservice.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(PaymentCompletedEvent event);

}
