package com.rate_service.exception;

// InvalidCurrencyException class to handle invalid currency code
public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
