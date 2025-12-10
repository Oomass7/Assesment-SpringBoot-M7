package com.example.coreservice.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Domain entity Affiliate (framework independent)
 * Represents a credit cooperative member
 */
public class Affiliate {
    private Long id;
    private String document;
    private String name;
    private Double salary;
    private LocalDate affiliationDate;
    private AffiliateStatus status;

    public enum AffiliateStatus {
        ACTIVE,
        INACTIVE
    }

    // Constructors
    public Affiliate() {
        this.affiliationDate = LocalDate.now();
        this.status = AffiliateStatus.ACTIVE;
    }

    public Affiliate(String document, String name, Double salary) {
        this();
        this.document = document;
        this.name = name;
        this.salary = salary;
    }

    // Business methods

    /**
     * Validates if the affiliate data is valid
     */
    public boolean isValid() {
        return document != null && !document.isEmpty()
                && name != null && !name.isEmpty()
                && salary != null && salary > 0;
    }

    /**
     * Checks if the affiliate is active
     */
    public boolean isActive() {
        return status == AffiliateStatus.ACTIVE;
    }

    /**
     * Activates the affiliate
     */
    public void activate() {
        this.status = AffiliateStatus.ACTIVE;
    }

    /**
     * Deactivates the affiliate
     */
    public void deactivate() {
        this.status = AffiliateStatus.INACTIVE;
    }

    /**
     * Calculates the number of months since affiliation
     */
    public long getMonthsSinceAffiliation() {
        if (affiliationDate == null)
            return 0;
        return ChronoUnit.MONTHS.between(affiliationDate, LocalDate.now());
    }

    /**
     * Checks if the affiliate has the minimum required seniority (6 months)
     */
    public boolean hasMinimumSeniority() {
        return getMonthsSinceAffiliation() >= 6;
    }

    /**
     * Calculates the maximum loan amount based on salary (e.g., 10x salary)
     */
    public Double getMaxLoanAmount() {
        return salary != null ? salary * 10 : 0.0;
    }

    /**
     * Validates if the affiliate can request a credit
     * Requirements: active, has minimum seniority
     */
    public boolean canRequestCredit() {
        return isActive() && hasMinimumSeniority();
    }

    /**
     * Calculates the monthly payment for a given amount and term
     */
    public Double calculateMonthlyPayment(Double amount, Integer termMonths, Double annualRate) {
        if (amount == null || termMonths == null || termMonths <= 0)
            return 0.0;
        double monthlyRate = (annualRate != null ? annualRate : 0.12) / 12;
        if (monthlyRate == 0)
            return amount / termMonths;
        return amount * (monthlyRate * Math.pow(1 + monthlyRate, termMonths))
                / (Math.pow(1 + monthlyRate, termMonths) - 1);
    }

    /**
     * Validates if the payment-to-income ratio is acceptable (max 40%)
     */
    public boolean isPaymentToIncomeRatioValid(Double monthlyPayment) {
        if (salary == null || salary <= 0 || monthlyPayment == null)
            return false;
        double ratio = monthlyPayment / salary;
        return ratio <= 0.40; // Maximum 40% of salary
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

    public AffiliateStatus getStatus() {
        return status;
    }

    public void setStatus(AffiliateStatus status) {
        this.status = status;
    }
}
