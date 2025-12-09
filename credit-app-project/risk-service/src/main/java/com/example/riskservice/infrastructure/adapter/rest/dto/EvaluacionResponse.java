package com.example.riskservice.infrastructure.adapter.rest.dto;

/**
 * DTO para respuesta de evaluación de riesgo
 */
public class EvaluacionResponse {
    private String documento;
    private Integer score;
    private String nivelRiesgo;

    // Constructores
    public EvaluacionResponse() {
    }

    public EvaluacionResponse(String documento, Integer score, String nivelRiesgo) {
        this.documento = documento;
        this.score = score;
        this.nivelRiesgo = nivelRiesgo;
    }

    // Getters y Setters
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }
}
