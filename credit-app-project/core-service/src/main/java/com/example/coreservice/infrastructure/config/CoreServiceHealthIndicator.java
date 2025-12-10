package com.example.coreservice.infrastructure.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom Health Indicator para Core Service
 * Verifica el estado de los componentes críticos del servicio
 */
@Component("coreServiceHealth")
public class CoreServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Verificar el estado de componentes críticos del servicio
        boolean serviceAvailable = checkServiceHealth();

        if (serviceAvailable) {
            return Health.up()
                    .withDetail("service", "Core Service")
                    .withDetail("status", "All systems operational")
                    .withDetail("version", "1.0.0")
                    .withDetail("description", "Credit application management service")
                    .build();
        }

        return Health.down()
                .withDetail("service", "Core Service")
                .withDetail("error", "Service is experiencing issues")
                .build();
    }

    /**
     * Verifica el estado general del servicio
     * En un escenario real, esto podría verificar conexiones a servicios externos,
     * colas de mensajes, cache, etc.
     */
    private boolean checkServiceHealth() {
        // Por ahora, siempre retorna true
        // En producción, aquí irían verificaciones reales
        return true;
    }
}
