package com.example.riskservice.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA para Evaluación
 */
@Entity
@Table(name = "evaluationes")
public class EvaluationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String document;
    
    private Double amount;
    
    private Integer term;
    
    @Column(nullable = false)
    private Integer score;
    
    @Column(name = "nivel_riesgo", nullable = false)
    private String riskLevel;
    
    @Column(name = "fecha_evaluation", nullable = false)
    private LocalDateTime evaluationDate;

    // Constructor por defecto
    public EvaluationEntity() {
        this.evaluationDate = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
