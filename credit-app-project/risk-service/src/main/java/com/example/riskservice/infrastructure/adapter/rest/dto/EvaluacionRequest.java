package com.example.riskservice.infrastructure.adapter.rest.dto;

/**
 * DTO para solicitud de evaluación de riesgo
 */
public class EvaluacionRequest {
    private String documento;
    private Double monto;
    private Integer plazo;

    // Constructores
    public EvaluacionRequest() {
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
}
