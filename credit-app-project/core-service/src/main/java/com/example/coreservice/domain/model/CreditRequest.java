package com.example.coreservice.domain.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio Solicitud de Crédito
 */
public class CreditRequest {
    private Long id;
    private String clientDocument;
    private String clientName;
    private Double requestedAmount;
    private Integer termMonths;
    private String status; // PENDIENTE, APROBADA, RECHAZADA
    private Integer riskScore;
    private String riskLevel; // BAJO, MEDIO, ALTO
    private LocalDateTime requestDate;
    private LocalDateTime evaluationDate;

    // Constructor
    public CreditRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDIENTE";
    }

    public CreditRequest(String clientDocument, String clientName, Double requestedAmount, Integer termMonths) {
        this();
        this.clientDocument = clientDocument;
        this.clientName = clientName;
        this.requestedAmount = requestedAmount;
        this.termMonths = termMonths;
    }

    // Métodos de negocio
    public boolean isValid() {
        return clientDocument != null && !clientDocument.isEmpty()
            && clientName != null && !clientName.isEmpty()
            && requestedAmount != null && requestedAmount > 0
            && termMonths != null && termMonths > 0;
    }

    public void approve() {
        this.status = "APROBADA";
        this.evaluationDate = LocalDateTime.now();
    }

    public void reject() {
        this.status = "RECHAZADA";
        this.evaluationDate = LocalDateTime.now();
    }

    public void assignRiskEvaluation(Integer score, String level) {
        this.riskScore = score;
        this.riskLevel = level;
        
        // Lógica de aprobación automática
        if ("BAJO".equals(level)) {
            approve();
        } else if ("ALTO".equals(level)) {
            reject();
        }
        // MEDIO requiere revisión manual
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
