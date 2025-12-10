package com.example.coreservice.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for credit request creation with validation
 */
public class RequestDTO {

    @NotBlank(message = "Client document is required")
    @Size(min = 5, max = 50, message = "Client document must be between 5 and 50 characters")
    private String clientDocument;

    @NotBlank(message = "Client name is required")
    @Size(min = 2, max = 255, message = "Client name must be between 2 and 255 characters")
    private String clientName;

    @NotNull(message = "Requested amount is required")
    @Positive(message = "Requested amount must be greater than 0")
    @Max(value = 1000000, message = "Requested amount cannot exceed 1,000,000")
    private Double requestedAmount;

    @NotNull(message = "Term months is required")
    @Min(value = 1, message = "Term must be at least 1 month")
    @Max(value = 360, message = "Term cannot exceed 360 months")
    private Integer termMonths;

    @DecimalMin(value = "0.0", message = "Proposed rate cannot be negative")
    @DecimalMax(value = "1.0", message = "Proposed rate cannot exceed 100% (1.0)")
    private Double proposedRate;

    // Constructors
    public RequestDTO() {
    }

    // Getters and Setters
    public String getClientDocument() {
        return clientDocument;
    }

    public void setClientDocument(String clientDocument) {
        this.clientDocument = clientDocument;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public Double getProposedRate() {
        return proposedRate;
    }

    public void setProposedRate(Double proposedRate) {
        this.proposedRate = proposedRate;
    }
}
