package com.example.riskservice.infrastructure.adapter.persistence;

import com.example.riskservice.application.port.out.EvaluationRepositoryPort;
import com.example.riskservice.domain.model.Evaluation;
import com.example.riskservice.infrastructure.adapter.persistence.mapper.EvaluationMapper;
import org.springframework.stereotype.Component;

/**
 * Adaptador de persistencia
 */
@Component
public class EvaluationRepositoryAdapter implements EvaluationRepositoryPort {

    private final JpaEvaluationRepository jpaRepository;
    private final EvaluationMapper mapper;

    public EvaluationRepositoryAdapter(JpaEvaluationRepository jpaRepository, EvaluationMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Evaluation save(Evaluation evaluation) {
        var entity = mapper.toEntity(evaluation);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
