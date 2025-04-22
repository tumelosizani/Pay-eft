package dev.dini.consentservice.exception;

public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException(String message) {
        super(message);
    }

    public ConsentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsentNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConsentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
