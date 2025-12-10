package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.infrastructure.adapter.persistence.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for credit requests
 */
@Repository
public interface JpaRequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findByClientDocument(String clientDocument);

    List<RequestEntity> findByStatus(String status);
}
