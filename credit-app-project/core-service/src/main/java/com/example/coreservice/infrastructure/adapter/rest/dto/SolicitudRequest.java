package com.example.coreservice.infrastructure.adapter.rest.dto;

/**
 * DTO para solicitud de crédito
 */
public class SolicitudRequest {
    private String documentoCliente;
    private String nombreCliente;
    private Double montoSolicitado;
    private Integer plazoMeses;

    // Constructores
    public SolicitudRequest() {
    }

    // Getters y Setters
    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
}
