package dev.dini.customerservice.mapper;


import dev.dini.customerservice.dto.CustomerCreateDTO;
import dev.dini.customerservice.dto.CustomerResponseDTO;
import dev.dini.customerservice.dto.CustomerUpdateDTO;
import dev.dini.customerservice.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponseDTO toResponseDTO(Customer customer);

    Customer toEntity(CustomerCreateDTO customerCreateDTO);

    void updateCustomer(CustomerUpdateDTO customerUpdateDTO, @MappingTarget Customer customer);
}
