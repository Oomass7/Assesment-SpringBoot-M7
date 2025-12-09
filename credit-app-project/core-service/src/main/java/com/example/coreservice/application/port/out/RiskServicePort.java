package com.example.coreservice.application.port.out;

import com.example.coreservice.domain.model.EvaluacionRiesgo;

/**
 * Puerto de salida - Interfaz para cliente de servicio de riesgo
 */
public interface RiskServicePort {
    EvaluacionRiesgo evaluar(String documento, Double monto, Integer plazo);
}
