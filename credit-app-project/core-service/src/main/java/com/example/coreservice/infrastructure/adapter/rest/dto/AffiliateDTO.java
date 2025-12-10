package com.example.coreservice.infrastructure.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * DTO for affiliate registration request with validation
 */
public class AffiliateDTO {

    @NotBlank(message = "Document is required")
    @Size(min = 5, max = 50, message = "Document must be between 5 and 50 characters")
    private String document;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be greater than 0")
    private Double salary;

    public AffiliateDTO() {
    }

    public AffiliateDTO(String document, String name, Double salary) {
        this.document = document;
        this.name = name;
        this.salary = salary;
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
}
