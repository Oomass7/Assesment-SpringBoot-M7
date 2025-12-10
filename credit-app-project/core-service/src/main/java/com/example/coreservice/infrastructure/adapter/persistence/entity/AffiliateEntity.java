package com.example.coreservice.infrastructure.adapter.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * JPA Entity for Affiliate
 */
@Entity
@Table(name = "affiliates")
public class AffiliateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String document;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "affiliation_date", nullable = false)
    private LocalDate affiliationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AffiliateStatusEntity status;

    public enum AffiliateStatusEntity {
        ACTIVE,
        INACTIVE
    }

    // Constructors
    public AffiliateEntity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getAffiliationDate() {
        return affiliationDate;
    }

    public void setAffiliationDate(LocalDate affiliationDate) {
        this.affiliationDate = affiliationDate;
    }

    public AffiliateStatusEntity getStatus() {
        return status;
    }

    public void setStatus(AffiliateStatusEntity status) {
        this.status = status;
    }
}
