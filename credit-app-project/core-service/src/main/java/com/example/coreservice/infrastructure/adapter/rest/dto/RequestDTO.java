package com.example.coreservice.infrastructure.adapter.rest.dto;

/**
 * DTO para request de crédito
 */
public class RequestDTO {
    private String clientDocument;
    private String clientName;
    private Double requestedAmount;
    private Integer termMonths;

    // Constructores
    public RequestDTO() {
    }

    // Getters y Setters
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
}
