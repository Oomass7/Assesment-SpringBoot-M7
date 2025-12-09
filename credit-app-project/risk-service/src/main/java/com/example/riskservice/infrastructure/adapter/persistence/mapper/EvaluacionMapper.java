package com.example.riskservice.infrastructure.adapter.persistence.mapper;

import com.example.riskservice.domain.model.Evaluacion;
import com.example.riskservice.infrastructure.adapter.persistence.entity.EvaluacionEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entidad de dominio y entidad JPA
 */
@Component
public class EvaluacionMapper {

    public Evaluacion toDomain(EvaluacionEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setDocumento(entity.getDocumento());
        evaluacion.setMonto(entity.getMonto());
        evaluacion.setPlazo(entity.getPlazo());
        evaluacion.setScore(entity.getScore());
        evaluacion.setNivelRiesgo(entity.getNivelRiesgo());
        
        return evaluacion;
    }

    public EvaluacionEntity toEntity(Evaluacion evaluacion) {
        if (evaluacion == null) {
            return null;
        }
        
        EvaluacionEntity entity = new EvaluacionEntity();
        entity.setDocumento(evaluacion.getDocumento());
        entity.setMonto(evaluacion.getMonto());
        entity.setPlazo(evaluacion.getPlazo());
        entity.setScore(evaluacion.getScore());
        entity.setNivelRiesgo(evaluacion.getNivelRiesgo());
        
        return entity;
    }
}
