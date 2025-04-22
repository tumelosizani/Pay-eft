package dev.dini.gatewayservice.mapper;

import dev.dini.gatewayservice.dto.PaymentCreateDTO;
import dev.dini.gatewayservice.dto.PaymentResponseDTO;
import dev.dini.gatewayservice.entity.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "initiatedAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
     PaymentRequest toEntity(PaymentCreateDTO paymentCreateDTO);

    PaymentResponseDTO toResponseDTO (PaymentRequest paymentRequest);
}
