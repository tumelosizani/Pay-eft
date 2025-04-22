package dev.dini.twofaservice.exception;

public class TwoFaNotFoundException extends RuntimeException {
    public TwoFaNotFoundException(String message) {
        super(message);
    }
}
