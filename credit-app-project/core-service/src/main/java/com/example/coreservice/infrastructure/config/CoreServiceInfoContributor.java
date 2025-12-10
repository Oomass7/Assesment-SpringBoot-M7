package com.example.coreservice.infrastructure.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Info Contributor para Core Service
 * Agrega información adicional al endpoint /actuator/info
 */
@Component
public class CoreServiceInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> serviceDetails = new HashMap<>();
        serviceDetails.put("name", "Core Service");
        serviceDetails.put("description", "Microservicio para gestión de solicitudes de crédito");
        serviceDetails.put("version", "1.0.0");
        serviceDetails.put("status", "active");

        Map<String, Object> buildInfo = new HashMap<>();
        buildInfo.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        buildInfo.put("javaVersion", System.getProperty("java.version"));
        buildInfo.put("springBootVersion", "3.1.4");

        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("affiliates", "/api/affiliates");
        endpoints.put("credits", "/api/credits");
        endpoints.put("swagger", "/swagger-ui.html");
        endpoints.put("actuator", "/actuator");

        builder.withDetail("service", serviceDetails);
        builder.withDetail("build", buildInfo);
        builder.withDetail("endpoints", endpoints);
    }
}
