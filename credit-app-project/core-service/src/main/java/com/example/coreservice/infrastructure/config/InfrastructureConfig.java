package com.example.coreservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de beans de infraestructura
 */
@Configuration
public class InfrastructureConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
