package com.example.riskservice.infrastructure.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Info Contributor para Risk Service
 * Agrega información adicional al endpoint /actuator/info
 */
@Component
public class RiskServiceInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> serviceDetails = new HashMap<>();
        serviceDetails.put("name", "Risk Service");
        serviceDetails.put("description", "Microservicio de evaluación de riesgo crediticio");
        serviceDetails.put("version", "1.0.0");
        serviceDetails.put("status", "active");

        Map<String, Object> buildInfo = new HashMap<>();
        buildInfo.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        buildInfo.put("javaVersion", System.getProperty("java.version"));
        buildInfo.put("springBootVersion", "3.1.4");

        Map<String, Object> riskEvaluation = new HashMap<>();
        riskEvaluation.put("engine", "Credit Risk Engine v1.0");
        riskEvaluation.put("scoringModel", "Combined Score Model");
        riskEvaluation.put("maxAmount", 50000);
        riskEvaluation.put("minAmount", 1000);

        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("evaluate", "/evaluate");
        endpoints.put("swagger", "/swagger-ui.html");
        endpoints.put("actuator", "/actuator");

        builder.withDetail("service", serviceDetails);
        builder.withDetail("build", buildInfo);
        builder.withDetail("riskEvaluation", riskEvaluation);
        builder.withDetail("endpoints", endpoints);
    }
}
