package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.application.port.out.RequestRepositoryPort;
import com.example.coreservice.domain.model.CreditRequest;
import com.example.coreservice.infrastructure.adapter.persistence.mapper.RequestMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistence adapter for credit requests
 */
@Component
public class RequestRepositoryAdapter implements RequestRepositoryPort {

    private final JpaRequestRepository jpaRepository;
    private final RequestMapper mapper;

    public RequestRepositoryAdapter(JpaRequestRepository jpaRepository, RequestMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CreditRequest save(CreditRequest request) {
        var entity = mapper.toEntity(request);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CreditRequest> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<CreditRequest> buscarTodas() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditRequest> findByDocument(String document) {
        return jpaRepository.findByClientDocument(document).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditRequest> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
