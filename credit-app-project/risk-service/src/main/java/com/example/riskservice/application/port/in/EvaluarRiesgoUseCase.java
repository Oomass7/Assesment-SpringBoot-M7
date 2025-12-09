package com.example.riskservice.application.port.in;

import com.example.riskservice.domain.model.Evaluacion;

/**
 * Puerto de entrada - Caso de uso para evaluar riesgo
 */
public interface EvaluarRiesgoUseCase {
    Evaluacion evaluar(String documento, Double monto, Integer plazo);
}
