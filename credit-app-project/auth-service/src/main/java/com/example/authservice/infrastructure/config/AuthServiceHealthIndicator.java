package com.example.authservice.infrastructure.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Custom Health Indicator para Auth Service
 * Verifica el estado del servicio de autenticación
 */
@Component("authServiceHealth")
public class AuthServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // Verificar el estado de componentes críticos del servicio
        boolean serviceAvailable = checkServiceHealth();

        if (serviceAvailable) {
            return Health.up()
                    .withDetail("service", "Auth Service")
                    .withDetail("status", "Authentication service operational")
                    .withDetail("version", "1.0.0")
                    .withDetail("description", "User authentication and authorization service")
                    .withDetail("jwtEnabled", true)
                    .build();
        }

        return Health.down()
                .withDetail("service", "Auth Service")
                .withDetail("error", "Authentication service is experiencing issues")
                .build();
    }

    /**
     * Verifica el estado general del servicio de autenticación
     * En un escenario real, esto podría verificar la conexión a la base de datos de
     * usuarios,
     * el estado del proveedor de identidad, etc.
     */
    private boolean checkServiceHealth() {
        // Por ahora, siempre retorna true
        // En producción, aquí irían verificaciones reales
        return true;
    }
}
