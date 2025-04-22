package dev.dini.customerservice.service;

import dev.dini.customerservice.dto.CustomerCreateDTO;
import dev.dini.customerservice.dto.CustomerResponseDTO;
import dev.dini.customerservice.dto.CustomerUpdateDTO;
import dev.dini.customerservice.entity.Customer;
import dev.dini.customerservice.exception.CustomerNotFoundException;
import dev.dini.customerservice.mapper.CustomerMapper;
import dev.dini.customerservice.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerWriteService, CustomerReadService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerResponseDTO> findCustomerByIdentifier(String identifier) {
        log.info("Find customer by identifier: {}", identifier);

        if (identifier == null || identifier.isEmpty()) {
            log.warn("Identifier is null or empty");
            return Optional.empty();
        }
        try {
            Optional<Customer> customerOptional = findCustomerByInternal(identifier);

            log.info("Customer found: {}", customerOptional);
            return customerOptional.map(customerMapper::toResponseDTO);
        } catch (CustomerNotFoundException e) {
            log.error("Exception occurred while finding customer: {}", e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<Customer> findCustomerByInternal(String identifier) {
        return customerRepository.findByIdentifier(identifier)
                .or(() -> {
                    throw new CustomerNotFoundException("Customer not found with identifier: " + identifier);
                });
    }

    @Override
    public CustomerResponseDTO createCustomer(@Valid CustomerCreateDTO customerCreateDTO) {
        Customer customer = customerMapper.toEntity(customerCreateDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(savedCustomer);
    }


    @Override
    public CustomerResponseDTO updateCustomer(UUID id, @Valid CustomerUpdateDTO updateDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customerMapper.updateCustomer(updateDTO, existingCustomer);
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        log.info("Delete customer with id: {}", customerId);
        customerRepository.deleteById(customerId);
    }

    @Override
    public Optional<CustomerResponseDTO> getCustomerById(UUID customerId) {
        log.info("Finding customer by ID: {}", customerId);
        return customerRepository.findById(customerId)
                .map(customerMapper::toResponseDTO)
                .or(() -> {
                    log.warn("Customer not found with ID: {}", customerId);
                    return Optional.empty();
                });
    }

}
