package dev.dini.customerservice.controller;

import dev.dini.customerservice.dto.*;
import dev.dini.customerservice.exception.CustomerNotFoundException;
import dev.dini.customerservice.service.CustomerReadService;
import dev.dini.customerservice.service.CustomerWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerWriteService customerWriteService;
    private final CustomerReadService customerReadService;


    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerCreateDTO customerCreateDTO) {
        log.info("Create customer: {}", customerCreateDTO);
        CustomerResponseDTO createdCustomer = customerWriteService.createCustomer(customerCreateDTO);
        log.info("Customer created: {}", createdCustomer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID customerId, @RequestBody CustomerUpdateDTO updateDTO) {
        log.info("Update customer with id: {} and data: {}", customerId, updateDTO);
        try {
            CustomerResponseDTO updatedCustomer = customerWriteService.updateCustomer(customerId, updateDTO);
            log.info("Customer updated: {}", updatedCustomer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException e) {
            log.warn("Customer not found for update with id: {}", customerId);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID customerId) {
        log.info("Delete customer with id: {}", customerId);
        try {
            customerWriteService.deleteCustomer(customerId);
            log.info("Customer deleted with id: {}", customerId);
            return ResponseEntity.noContent().build();
        } catch (CustomerNotFoundException e) {
            log.warn("Customer not found for deletion with id: {}", customerId);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<CustomerResponseDTO> verifyCustomer(@RequestParam String identifier) {
        log.info("Verify customer with identifier: {}", identifier);

        Optional<CustomerResponseDTO> customerResponseDTOOptional = customerReadService.findCustomerByIdentifier(identifier);
        return customerResponseDTOOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable UUID customerId) {
        log.info("Get customer by id: {}", customerId);
        Optional<CustomerResponseDTO> customerResponse = customerReadService.getCustomerById(customerId);

        return customerResponse.map(response -> {
                    log.info("Customer found with id: {}", customerId);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("Customer not found with id: {}", customerId);
                    return ResponseEntity.notFound().build();
                });
    }
}
