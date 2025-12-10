package com.example.riskservice.application.port.in;

import com.example.riskservice.domain.model.Evaluation;

/**
 * Puerto de entrada - Caso de uso para evaluar riesgo
 */
public interface EvaluateRiskUseCase {
    Evaluation evaluate(String document, Double amount, Integer term);
}
