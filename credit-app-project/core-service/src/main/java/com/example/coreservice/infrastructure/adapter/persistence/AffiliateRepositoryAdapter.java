package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.application.port.out.AffiliateRepositoryPort;
import com.example.coreservice.domain.model.Affiliate;
import com.example.coreservice.infrastructure.adapter.persistence.mapper.AffiliateMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter for affiliates
 */
@Component
public class AffiliateRepositoryAdapter implements AffiliateRepositoryPort {

    private final JpaAffiliateRepository jpaRepository;
    private final AffiliateMapper mapper;

    public AffiliateRepositoryAdapter(JpaAffiliateRepository jpaRepository, AffiliateMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Affiliate save(Affiliate affiliate) {
        var entity = mapper.toEntity(affiliate);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Affiliate> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Affiliate> findByDocument(String document) {
        return jpaRepository.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public List<Affiliate> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByDocument(String document) {
        return jpaRepository.existsByDocument(document);
    }
}
