package com.example.coreservice.domain.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio Solicitud de Crédito
 */
public class SolicitudCredito {
    private Long id;
    private String documentoCliente;
    private String nombreCliente;
    private Double montoSolicitado;
    private Integer plazoMeses;
    private String estado; // PENDIENTE, APROBADA, RECHAZADA
    private Integer scoreRiesgo;
    private String nivelRiesgo; // BAJO, MEDIO, ALTO
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaEvaluacion;

    // Constructor
    public SolicitudCredito() {
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    public SolicitudCredito(String documentoCliente, String nombreCliente, Double montoSolicitado, Integer plazoMeses) {
        this();
        this.documentoCliente = documentoCliente;
        this.nombreCliente = nombreCliente;
        this.montoSolicitado = montoSolicitado;
        this.plazoMeses = plazoMeses;
    }

    // Métodos de negocio
    public boolean esValida() {
        return documentoCliente != null && !documentoCliente.isEmpty()
            && nombreCliente != null && !nombreCliente.isEmpty()
            && montoSolicitado != null && montoSolicitado > 0
            && plazoMeses != null && plazoMeses > 0;
    }

    public void aprobar() {
        this.estado = "APROBADA";
        this.fechaEvaluacion = LocalDateTime.now();
    }

    public void rechazar() {
        this.estado = "RECHAZADA";
        this.fechaEvaluacion = LocalDateTime.now();
    }

    public void asignarEvaluacionRiesgo(Integer score, String nivel) {
        this.scoreRiesgo = score;
        this.nivelRiesgo = nivel;
        
        // Lógica de aprobación automática
        if ("BAJO".equals(nivel)) {
            aprobar();
        } else if ("ALTO".equals(nivel)) {
            rechazar();
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getScoreRiesgo() {
        return scoreRiesgo;
    }

    public void setScoreRiesgo(Integer scoreRiesgo) {
        this.scoreRiesgo = scoreRiesgo;
    }

    public String getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(String nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDateTime fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }
}
