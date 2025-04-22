package dev.dini.gatewayservice.controller;

import dev.dini.gatewayservice.dto.PaymentCreateDTO;
import dev.dini.gatewayservice.dto.PaymentResponseDTO;
import dev.dini.gatewayservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(
            @Valid @RequestBody PaymentCreateDTO paymentCreateDTO) {
        PaymentResponseDTO response = paymentService.initiatePayment(paymentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
