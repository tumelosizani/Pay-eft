package dev.dini.customerservice.service;

import dev.dini.customerservice.dto.CustomerCreateDTO;
import dev.dini.customerservice.dto.CustomerResponseDTO;
import dev.dini.customerservice.dto.CustomerUpdateDTO;

import java.util.UUID;

public interface CustomerWriteService {
    CustomerResponseDTO createCustomer(CustomerCreateDTO customerCreateDTO);
    CustomerResponseDTO updateCustomer(UUID id, CustomerUpdateDTO updateDTO);


    void deleteCustomer(UUID customerId);
}
