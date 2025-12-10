package com.example.authservice.infrastructure.adapter.persistence;

import com.example.authservice.application.port.out.UserRepositoryPort;
import com.example.authservice.domain.model.User;
import com.example.authservice.infrastructure.adapter.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de persistencia - Implementación del puerto de repositorio
 */
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaRepository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(JpaUserRepository jpaRepository, UserMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        var entity = mapper.toEntity(user);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
