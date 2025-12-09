package com.example.riskservice.application.service;

import com.example.riskservice.application.port.in.EvaluarRiesgoUseCase;
import com.example.riskservice.application.port.out.EvaluacionRepositoryPort;
import com.example.riskservice.domain.model.Evaluacion;
import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación para evaluación de riesgo
 */
@Service
public class RiskService implements EvaluarRiesgoUseCase {

    private final EvaluacionRepositoryPort evaluacionRepository;

    public RiskService(EvaluacionRepositoryPort evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    @Override
    public Evaluacion evaluar(String documento, Double monto, Integer plazo) {
        // Validar entrada
        if (documento == null || documento.isEmpty()) {
            throw new IllegalArgumentException("El documento es requerido");
        }

        // Crear evaluación
        Evaluacion evaluacion = new Evaluacion(documento, monto, plazo);
        
        // Validar
        if (!evaluacion.esValida()) {
            throw new IllegalArgumentException("Datos de evaluación inválidos");
        }

        // Calcular riesgo (lógica de negocio)
        evaluacion.calcularRiesgo();

        // Guardar historial de evaluación
        evaluacionRepository.guardar(evaluacion);

        return evaluacion;
    }
}
