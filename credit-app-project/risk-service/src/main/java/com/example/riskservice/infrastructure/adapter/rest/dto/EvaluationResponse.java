package com.example.riskservice.infrastructure.adapter.rest.dto;

/**
 * DTO para respuesta de evaluación de riesgo
 */
public class EvaluationResponse {
    private String document;
    private Integer score;
    private String riskLevel;

    // Constructores
    public EvaluationResponse() {
    }

    public EvaluationResponse(String document, Integer score, String levelRiesgo) {
        this.document = document;
        this.score = score;
        this.riskLevel = riskLevel;
    }

    // Getters y Setters
    public String getClientDocument() {
        return document;
    }

    public void setClientDocument(String document) {
        this.document = document;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String levelRiesgo) {
        this.riskLevel = riskLevel;
    }
}
