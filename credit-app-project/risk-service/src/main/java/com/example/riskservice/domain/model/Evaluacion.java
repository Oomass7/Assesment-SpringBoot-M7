package com.example.riskservice.domain.model;

/**
 * Entidad de dominio para Evaluación de Riesgo
 */
public class Evaluacion {
    private String documento;
    private Double monto;
    private Integer plazo;
    private Integer score;
    private String nivelRiesgo; // BAJO, MEDIO, ALTO

    // Constructor
    public Evaluacion() {
    }

    public Evaluacion(String documento, Double monto, Integer plazo) {
        this.documento = documento;
        this.monto = monto;
        this.plazo = plazo;
    }

    // Métodos de negocio
    public void calcularRiesgo() {
        // Algoritmo de evaluación de riesgo basado en documento
        int baseScore = Math.abs(documento.hashCode()) % 651 + 300;
        
        // Ajustar score según monto
        if (monto != null && monto > 50000) {
            baseScore += 50; // Mayor riesgo para montos altos
        }
        
        // Ajustar score según plazo
        if (plazo != null && plazo > 36) {
            baseScore += 30; // Mayor riesgo para plazos largos
        }
        
        this.score = baseScore;
        
        // Determinar nivel de riesgo
        if (score <= 500) {
            this.nivelRiesgo = "ALTO";
        } else if (score <= 700) {
            this.nivelRiesgo = "MEDIO";
        } else {
            this.nivelRiesgo = "BAJO";
        }
    }

    public boolean esValida() {
        return documento != null && !documento.isEmpty();
    }

    // Getters y Setters
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
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
