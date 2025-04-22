package dev.dini.gatewayservice.exception;

public class CustomerVerificationFailedException extends RuntimeException {
  public CustomerVerificationFailedException(String message) {
    super(message);
  }
}
