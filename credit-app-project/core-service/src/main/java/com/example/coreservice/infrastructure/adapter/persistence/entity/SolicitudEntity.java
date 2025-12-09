package com.example.coreservice.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA para Solicitud
 */
@Entity
@Table(name = "solicitudes")
public class SolicitudEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "documento_cliente", nullable = false)
    private String documentoCliente;
    
    @Column(name = "nombre_cliente", nullable = false)
    private String nombreCliente;
    
    @Column(name = "monto_solicitado", nullable = false)
    private Double montoSolicitado;
    
    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;
    
    @Column(nullable = false)
    private String estado;
    
    @Column(name = "score_riesgo")
    private Integer scoreRiesgo;
    
    @Column(name = "nivel_riesgo")
    private String nivelRiesgo;
    
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;
    
    @Column(name = "fecha_evaluacion")
    private LocalDateTime fechaEvaluacion;

    // Constructor por defecto
    public SolicitudEntity() {
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = "PENDIENTE";
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
