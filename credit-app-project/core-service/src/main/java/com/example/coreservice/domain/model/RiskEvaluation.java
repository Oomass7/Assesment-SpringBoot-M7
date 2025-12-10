package com.example.coreservice.domain.model;

/**
 * Value Object for risk evaluation
 */
public class RiskEvaluation {
    private final String document;
    private final Integer score;
    private final String riskLevel;

    public RiskEvaluation(String document, Integer score, String riskLevel) {
        this.document = document;
        this.score = score;
        this.riskLevel = riskLevel;
    }

    public String getDocument() {
        return document;
    }

    public Integer getScore() {
        return score;
    }

    public String getRiskLevel() {
        return riskLevel;
    }
}
