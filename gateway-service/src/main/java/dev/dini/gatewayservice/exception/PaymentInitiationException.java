package dev.dini.gatewayservice.exception;

public class PaymentInitiationException extends RuntimeException {
    public PaymentInitiationException(String message, Exception e) {
        super(message);
    }
}
