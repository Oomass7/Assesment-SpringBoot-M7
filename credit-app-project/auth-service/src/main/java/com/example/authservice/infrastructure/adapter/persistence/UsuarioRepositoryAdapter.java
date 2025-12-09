package com.example.authservice.infrastructure.adapter.persistence;

import com.example.authservice.application.port.out.UsuarioRepositoryPort;
import com.example.authservice.domain.model.Usuario;
import com.example.authservice.infrastructure.adapter.persistence.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de persistencia - Implementación del puerto de repositorio
 */
@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final JpaUsuarioRepository jpaRepository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaRepository, UsuarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        var entity = mapper.toEntity(usuario);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
