package com.example.riskservice.infrastructure.adapter.persistence;

import com.example.riskservice.infrastructure.adapter.persistence.entity.EvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA Spring Data
 */
@Repository
public interface JpaEvaluationRepository extends JpaRepository<EvaluationEntity, Long> {
}
