package com.example.riskservice.infrastructure.adapter.rest.dto;

/**
 * DTO para request de evaluación de riesgo
 */
public class EvaluationRequest {
    private String document;
    private Double amount;
    private Integer term;

    // Constructores
    public EvaluationRequest() {
    }

    // Getters y Setters
    public String getClientDocument() {
        return document;
    }

    public void setClientDocument(String document) {
        this.document = document;
    }

    public Double getRequestedAmount() {
        return amount;
    }

    public void setRequestedAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTermMonths() {
        return term;
    }

    public void setTermMonths(Integer term) {
        this.term = term;
    }
}
