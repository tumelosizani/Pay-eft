package dev.dini.gatewayservice.service;

import dev.dini.gatewayservice.client.*;
import dev.dini.gatewayservice.dto.*;
import dev.dini.gatewayservice.entity.*;
import dev.dini.gatewayservice.exception.*;
import dev.dini.gatewayservice.mapper.PaymentRequestMapper;
import dev.dini.gatewayservice.message.*;
import dev.dini.gatewayservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerServiceClient customerServiceClient;
    private final PaymentRequestMapper paymentRequestMapper;
    private final PaymentInitiatedProducer paymentInitiatedProducer;
    private final PaymentCompletedProducer paymentCompletedProducer;


    @Override
    public PaymentResponseDTO initiatePayment(PaymentCreateDTO paymentCreateDTO) {

        log.info("Initiating payment for customer with identifier: {}", paymentCreateDTO.identifier());

        // 1. Validate customer
        try {
            CustomerDTO customer = customerServiceClient.verifyCustomer(paymentCreateDTO.identifier());
            log.info("Customer found: {}", customer);

            // 2. Create and save payment request
            PaymentRequest payment = paymentRequestMapper.toEntity(paymentCreateDTO);
            payment.setCustomerId(customer.id());
            payment.setStatus(PaymentStatus.PENDING_2FA);
            PaymentRequest savedPayment = paymentRepository.save(payment);

            // 3. Trigger 2FA - kafka
            PaymentInitiatedEvent event = new PaymentInitiatedEvent(savedPayment.getId(), customer.id());
            paymentInitiatedProducer.publishPaymentInitiatedEvent(event);

            return paymentRequestMapper.toResponseDTO(savedPayment);
        } catch (CustomerVerificationFailedException e) {
            log.error("Customer verification failed for identifier {}: {}", paymentCreateDTO.identifier(), e.getMessage());
            throw new PaymentInitiationException("Failed to verify customer.", e);
        } catch (Exception e) {
            log.error("Error initiating payment for customer {}: {}", paymentCreateDTO.identifier(), e.getMessage());
            throw new PaymentInitiationException("Failed to initiate payment.", e);
        }
    }

    @Override
    public void updatePaymentStatus(UUID paymentRequestId, PaymentStatus status) {
        paymentRepository.findById(paymentRequestId)
                .ifPresentOrElse(
                        // 3. Update payment status
                        paymentRequest -> {
                            // Check if the status is valid
                            paymentRequest.setStatus(status);
                            paymentRepository.save(paymentRequest);
                            log.info("Payment request {} updated to status: {}", paymentRequestId, status);
                        },
                        () -> log.warn("Payment request with ID: {} not found", paymentRequestId)
                );
    }

    @Override
    public void completePayment(UUID paymentRequestId) {
        paymentRepository.findById(paymentRequestId)
                .ifPresentOrElse(

                        paymentRequest -> {
                            if (paymentRequest.getStatus() == PaymentStatus.APPROVED) {
                                paymentRequest.setStatus(PaymentStatus.COMPLETED);
                                paymentRepository.save(paymentRequest);
                                log.info("Payment request {} updated to status: COMPLETED", paymentRequestId);
                                PaymentCompletedEvent event = new PaymentCompletedEvent(
                                        paymentRequestId,
                                        paymentRequest.getCustomerId(),
                                        paymentRequest.getAmount()
                                );
                                // 4. Trigger payment completed - kafka
                                paymentCompletedProducer.publishPaymentCompletedEvent(event);
                                log.info("Published PaymentCompletedEvent for request: {}", paymentRequestId);
                            } else {
                                log.warn("Payment request {} cannot be completed as its current status is {}", paymentRequestId, paymentRequest.getStatus());
                            }
                        },
                        () -> log.warn("Payment request with ID {} not found", paymentRequestId)
                );
    }


}
