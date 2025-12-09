package com.example.riskservice.application.port.out;

import com.example.riskservice.domain.model.Evaluacion;

/**
 * Puerto de salida - Interfaz para persistencia de evaluaciones
 */
public interface EvaluacionRepositoryPort {
    Evaluacion guardar(Evaluacion evaluacion);
}
