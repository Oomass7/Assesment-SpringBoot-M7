package com.example.coreservice.domain.model;

import java.time.LocalDateTime;

/**
 * Domain entity Credit Request
 */
public class CreditRequest {
    private Long id;
    private Long affiliateId;
    private String clientDocument;
    private String clientName;
    private Double requestedAmount;
    private Integer termMonths;
    private Double proposedRate; // Annual interest rate
    private String status; // PENDING, APPROVED, REJECTED
    private Integer riskScore;
    private String riskLevel; // LOW, MEDIUM, HIGH
    private LocalDateTime requestDate;
    private LocalDateTime evaluationDate;
    private String rejectionReason;

    // Constructor
    public CreditRequest() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public CreditRequest(String clientDocument, String clientName, Double requestedAmount, Integer termMonths) {
        this();
        this.clientDocument = clientDocument;
        this.clientName = clientName;
        this.requestedAmount = requestedAmount;
        this.termMonths = termMonths;
    }

    public CreditRequest(String clientDocument, String clientName, Double requestedAmount, Integer termMonths,
            Double proposedRate) {
        this(clientDocument, clientName, requestedAmount, termMonths);
        this.proposedRate = proposedRate;
    }

    // Business methods
    public boolean isValid() {
        return clientDocument != null && !clientDocument.isEmpty()
                && clientName != null && !clientName.isEmpty()
                && requestedAmount != null && requestedAmount > 0
                && termMonths != null && termMonths > 0;
    }

    public void approve() {
        this.status = "APPROVED";
        this.evaluationDate = LocalDateTime.now();
    }

    public void reject(String reason) {
        this.status = "REJECTED";
        this.rejectionReason = reason;
        this.evaluationDate = LocalDateTime.now();
    }

    public void reject() {
        reject("Risk level too high");
    }

    public void assignRiskEvaluation(Integer score, String level) {
        this.riskScore = score;
        this.riskLevel = level;

        // Automatic approval logic
        if ("LOW".equals(level) || "BAJO".equals(level)) {
            approve();
        } else if ("HIGH".equals(level) || "ALTO".equals(level)) {
            reject("High risk level");
        }
        // MEDIUM requires manual review
    }

    /**
     * Calculate monthly payment
     */
    public Double calculateMonthlyPayment() {
        if (requestedAmount == null || termMonths == null || termMonths <= 0)
            return 0.0;
        double rate = proposedRate != null ? proposedRate : 0.12; // Default 12% annual
        double monthlyRate = rate / 12;
        if (monthlyRate == 0)
            return requestedAmount / termMonths;
        return requestedAmount * (monthlyRate * Math.pow(1 + monthlyRate, termMonths))
                / (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Long affiliateId) {
        this.affiliateId = affiliateId;
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

    public Double getProposedRate() {
        return proposedRate;
    }

    public void setProposedRate(Double proposedRate) {
        this.proposedRate = proposedRate;
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

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
