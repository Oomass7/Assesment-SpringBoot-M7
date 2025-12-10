package com.example.coreservice.infrastructure.adapter.rest.dto;

import java.time.LocalDate;

/**
 * DTO for affiliate response
 */
public class AffiliateResponse {
    private Long id;
    private String document;
    private String name;
    private Double salary;
    private LocalDate affiliationDate;
    private String status;
    private Long monthsSinceAffiliation;
    private Double maxLoanAmount;
    private Boolean canRequestCredit;

    public AffiliateResponse() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMonthsSinceAffiliation() {
        return monthsSinceAffiliation;
    }

    public void setMonthsSinceAffiliation(Long monthsSinceAffiliation) {
        this.monthsSinceAffiliation = monthsSinceAffiliation;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Boolean getCanRequestCredit() {
        return canRequestCredit;
    }

    public void setCanRequestCredit(Boolean canRequestCredit) {
        this.canRequestCredit = canRequestCredit;
    }
}
