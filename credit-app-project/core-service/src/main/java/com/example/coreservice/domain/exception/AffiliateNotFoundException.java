package com.example.coreservice.domain.exception;

/**
 * Exception thrown when an affiliate is not found
 */
public class AffiliateNotFoundException extends RuntimeException {
    public AffiliateNotFoundException(String message) {
        super(message);
    }
}
