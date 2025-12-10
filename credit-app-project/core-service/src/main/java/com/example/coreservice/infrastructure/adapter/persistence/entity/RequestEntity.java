package com.example.coreservice.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA para Solicitud
 */
@Entity
@Table(name = "requestes")
public class RequestEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "documento_cliente", nullable = false)
    private String clientDocument;
    
    @Column(name = "nombre_cliente", nullable = false)
    private String clientName;
    
    @Column(name = "monto_solicitado", nullable = false)
    private Double requestedAmount;
    
    @Column(name = "plazo_meses", nullable = false)
    private Integer termMonths;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "score_riesgo")
    private Integer riskScore;
    
    @Column(name = "nivel_riesgo")
    private String riskLevel;
    
    @Column(name = "fecha_request", nullable = false)
    private LocalDateTime requestDate;
    
    @Column(name = "fecha_evaluation")
    private LocalDateTime evaluationDate;

    // Constructor por defecto
    public RequestEntity() {
        this.requestDate = LocalDateTime.now();
        this.status = "PENDIENTE";
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientDocument() {
        return clientDocument;
    }

    public void setClientDocument(String clientDocument) {
        this.clientDocument = clientDocument;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
