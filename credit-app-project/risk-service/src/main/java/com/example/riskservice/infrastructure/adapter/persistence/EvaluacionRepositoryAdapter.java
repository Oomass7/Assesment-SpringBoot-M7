package com.example.riskservice.infrastructure.adapter.persistence;

import com.example.riskservice.application.port.out.EvaluacionRepositoryPort;
import com.example.riskservice.domain.model.Evaluacion;
import com.example.riskservice.infrastructure.adapter.persistence.mapper.EvaluacionMapper;
import org.springframework.stereotype.Component;

/**
 * Adaptador de persistencia
 */
@Component
public class EvaluacionRepositoryAdapter implements EvaluacionRepositoryPort {

    private final JpaEvaluacionRepository jpaRepository;
    private final EvaluacionMapper mapper;

    public EvaluacionRepositoryAdapter(JpaEvaluacionRepository jpaRepository, EvaluacionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Evaluacion guardar(Evaluacion evaluacion) {
        var entity = mapper.toEntity(evaluacion);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
}
