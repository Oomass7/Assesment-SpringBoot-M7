package com.example.riskservice.infrastructure.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom Health Indicator para Risk Service
 * Verifica el estado del servicio de evaluación de riesgo
 */
@Component("riskServiceHealth")
public class RiskServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Verificar el estado de componentes críticos del servicio
        boolean serviceAvailable = checkRiskEngineHealth();

        if (serviceAvailable) {
            return Health.up()
                    .withDetail("service", "Risk Service")
                    .withDetail("status", "Risk evaluation engine operational")
                    .withDetail("version", "1.0.0")
                    .withDetail("description", "Credit risk assessment service")
                    .withDetail("riskEngineEnabled", true)
                    .build();
        }

        return Health.down()
                .withDetail("service", "Risk Service")
                .withDetail("error", "Risk evaluation engine is experiencing issues")
                .build();
    }

    /**
     * Verifica el estado del motor de evaluación de riesgo
     * En un escenario real, esto podría verificar la conexión a modelos de ML,
     * servicios externos de scoring, etc.
     */
    private boolean checkRiskEngineHealth() {
        // Por ahora, siempre retorna true
        // En producción, aquí irían verificaciones reales
        return true;
    }
}
