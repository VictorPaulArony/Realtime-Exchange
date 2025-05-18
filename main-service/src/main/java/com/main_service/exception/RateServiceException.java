package com.main_service.exception;

public class RateServiceException extends RuntimeException {
    public RateServiceException(String message) {
        super(message);
    }

    public RateServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
