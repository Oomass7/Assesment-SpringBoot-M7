package com.example.riskservice.domain.model;

/**
 * Entidad de dominio para Evaluación de Riesgo
 */
public class Evaluation {
    private String document;
    private Double amount;
    private Integer term;
    private Integer score;
    private String riskLevel; // BAJO, MEDIO, ALTO

    // Constructor
    public Evaluation() {
    }

    public Evaluation(String document, Double amount, Integer term) {
        this.document = document;
        this.amount = amount;
        this.term = term;
    }

    // Métodos de negocio
    public void calculateRisk() {
        // Algoritmo de evaluación de riesgo basado en documento
        int baseScore = Math.abs(document.hashCode()) % 651 + 300;
        
        // Ajustar score según monto
        if (amount != null && amount > 50000) {
            baseScore += 50; // Mayor riesgo para montos altos
        }
        
        // Ajustar score según plazo
        if (term != null && term > 36) {
            baseScore += 30; // Mayor riesgo para plazos largos
        }
        
        this.score = baseScore;
        
        // Determinar nivel de riesgo
        if (score <= 500) {
            this.riskLevel = "ALTO";
        } else if (score <= 700) {
            this.riskLevel = "MEDIO";
        } else {
            this.riskLevel = "BAJO";
        }
    }

    public boolean isValid() {
        return document != null && !document.isEmpty();
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
