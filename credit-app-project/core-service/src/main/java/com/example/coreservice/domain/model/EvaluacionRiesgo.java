package com.example.coreservice.domain.model;

/**
 * Value Object para evaluación de riesgo
 */
public class EvaluacionRiesgo {
    private final String documento;
    private final Integer score;
    private final String nivelRiesgo;

    public EvaluacionRiesgo(String documento, Integer score, String nivelRiesgo) {
        this.documento = documento;
        this.score = score;
        this.nivelRiesgo = nivelRiesgo;
    }

    public String getDocumento() {
        return documento;
    }

    public Integer getScore() {
        return score;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }
}
