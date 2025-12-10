package com.example.coreservice.infrastructure.config;

import com.example.coreservice.domain.exception.AffiliateNotFoundException;
import com.example.coreservice.domain.exception.AffiliateNotEligibleException;
import com.example.coreservice.domain.exception.RequestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler implementing RFC 7807 ProblemDetail
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String PROBLEM_BASE_URI = "https://api.creditapp.com/problems";

    /**
     * Handle Bean Validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields");

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/validation-error"));
        problemDetail.setTitle("Validation Error");

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    /**
     * Handle affiliate not found
     */
    @ExceptionHandler(AffiliateNotFoundException.class)
    public ProblemDetail handleAffiliateNotFound(AffiliateNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/affiliate-not-found"));
        problemDetail.setTitle("Affiliate Not Found");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * Handle affiliate not eligible for credit
     */
    @ExceptionHandler(AffiliateNotEligibleException.class)
    public ProblemDetail handleAffiliateNotEligible(AffiliateNotEligibleException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                ex.getMessage());

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/affiliate-not-eligible"));
        problemDetail.setTitle("Affiliate Not Eligible");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * Handle request not found
     */
    @ExceptionHandler(RequestNotFoundException.class)
    public ProblemDetail handleRequestNotFound(RequestNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage());

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/request-not-found"));
        problemDetail.setTitle("Credit Request Not Found");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * Handle illegal argument exceptions (business validation)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/invalid-request"));
        problemDetail.setTitle("Invalid Request");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * Handle access denied exceptions
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "You do not have permission to access this resource");

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/access-denied"));
        problemDetail.setTitle("Access Denied");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred");

        problemDetail.setType(URI.create(PROBLEM_BASE_URI + "/internal-error"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("exception", ex.getClass().getSimpleName());

        return problemDetail;
    }
}
