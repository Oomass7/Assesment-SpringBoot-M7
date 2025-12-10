package com.example.coreservice.application.port.out;

import com.example.coreservice.domain.model.RiskEvaluation;

/**
 * Puerto de salida - Interfaz para cliente de servicio de riesgo
 */
public interface RiskServicePort {
    RiskEvaluation evaluate(String document, Double amount, Integer term);
}
