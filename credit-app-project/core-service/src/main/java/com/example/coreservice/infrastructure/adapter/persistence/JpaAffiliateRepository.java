package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.infrastructure.adapter.persistence.entity.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for affiliates
 */
@Repository
public interface JpaAffiliateRepository extends JpaRepository<AffiliateEntity, Long> {
    Optional<AffiliateEntity> findByDocument(String document);

    boolean existsByDocument(String document);
}
