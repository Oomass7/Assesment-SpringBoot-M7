package com.example.authservice.infrastructure.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Info Contributor para Auth Service
 * Agrega información adicional al endpoint /actuator/info
 */
@Component
public class AuthServiceInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> serviceDetails = new HashMap<>();
        serviceDetails.put("name", "Auth Service");
        serviceDetails.put("description", "Microservicio de autenticación y autorización");
        serviceDetails.put("version", "1.0.0");
        serviceDetails.put("status", "active");

        Map<String, Object> buildInfo = new HashMap<>();
        buildInfo.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        buildInfo.put("javaVersion", System.getProperty("java.version"));
        buildInfo.put("springBootVersion", "3.1.4");

        Map<String, Object> securityFeatures = new HashMap<>();
        securityFeatures.put("jwtAuthentication", true);
        securityFeatures.put("bcryptPasswordHashing", true);
        securityFeatures.put("roleBasedAccessControl", true);

        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("login", "/auth/login");
        endpoints.put("register", "/auth/register");
        endpoints.put("swagger", "/swagger-ui.html");
        endpoints.put("actuator", "/actuator");

        builder.withDetail("service", serviceDetails);
        builder.withDetail("build", buildInfo);
        builder.withDetail("security", securityFeatures);
        builder.withDetail("endpoints", endpoints);
    }
}
