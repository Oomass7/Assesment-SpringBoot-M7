package com.example.riskservice.infrastructure.adapter.persistence.mapper;

import com.example.riskservice.domain.model.Evaluation;
import com.example.riskservice.infrastructure.adapter.persistence.entity.EvaluationEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entidad de dominio y entidad JPA
 */
@Component
public class EvaluationMapper {

    public Evaluation toDomain(EvaluationEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Evaluation evaluation = new Evaluation();
        evaluation.setClientDocument(entity.getClientDocument());
        evaluation.setRequestedAmount(entity.getRequestedAmount());
        evaluation.setTermMonths(entity.getTermMonths());
        evaluation.setScore(entity.getScore());
        evaluation.setRiskLevel(entity.getRiskLevel());
        
        return evaluation;
    }

    public EvaluationEntity toEntity(Evaluation evaluation) {
        if (evaluation == null) {
            return null;
        }
        
        EvaluationEntity entity = new EvaluationEntity();
        entity.setClientDocument(evaluation.getClientDocument());
        entity.setRequestedAmount(evaluation.getRequestedAmount());
        entity.setTermMonths(evaluation.getTermMonths());
        entity.setScore(evaluation.getScore());
        entity.setRiskLevel(evaluation.getRiskLevel());
        
        return entity;
    }
}
