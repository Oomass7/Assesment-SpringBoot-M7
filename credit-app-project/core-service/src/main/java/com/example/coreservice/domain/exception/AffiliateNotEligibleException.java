package com.example.coreservice.domain.exception;

/**
 * Exception thrown when an affiliate cannot request credit
 */
public class AffiliateNotEligibleException extends RuntimeException {
    public AffiliateNotEligibleException(String message) {
        super(message);
    }
}
