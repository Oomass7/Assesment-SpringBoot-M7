package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.application.port.out.SolicitudRepositoryPort;
import com.example.coreservice.domain.model.SolicitudCredito;
import com.example.coreservice.infrastructure.adapter.persistence.mapper.SolicitudMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistencia
 */
@Component
public class SolicitudRepositoryAdapter implements SolicitudRepositoryPort {

    private final JpaSolicitudRepository jpaRepository;
    private final SolicitudMapper mapper;

    public SolicitudRepositoryAdapter(JpaSolicitudRepository jpaRepository, SolicitudMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public SolicitudCredito guardar(SolicitudCredito solicitud) {
        var entity = mapper.toEntity(solicitud);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<SolicitudCredito> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<SolicitudCredito> buscarTodas() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SolicitudCredito> buscarPorDocumento(String documento) {
        return jpaRepository.findByDocumentoCliente(documento).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
