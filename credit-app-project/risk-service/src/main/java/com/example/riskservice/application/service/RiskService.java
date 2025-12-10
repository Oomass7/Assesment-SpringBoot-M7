package com.example.riskservice.application.service;

import com.example.riskservice.application.port.in.EvaluateRiskUseCase;
import com.example.riskservice.application.port.out.EvaluationRepositoryPort;
import com.example.riskservice.domain.model.Evaluation;
import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación para evaluación de riesgo
 */
@Service
public class RiskService implements EvaluateRiskUseCase {

    private final EvaluationRepositoryPort evaluationRepository;

    public RiskService(EvaluationRepositoryPort evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public Evaluation evaluate(String document, Double amount, Integer term) {
        // Validar entrada
        if (document == null || document.isEmpty()) {
            throw new IllegalArgumentException("Document is required");
        }

        // Crear evaluación
        Evaluation evaluation = new Evaluation(document, amount, term);
        
        // Validar
        if (!evaluation.isValid()) {
            throw new IllegalArgumentException("Datos de evaluación inválidos");
        }

        // Calcular riesgo (lógica de negocio)
        evaluation.calculateRisk();

        // Guardar historial de evaluación
        evaluationRepository.save(evaluation);

        return evaluation;
    }
}
