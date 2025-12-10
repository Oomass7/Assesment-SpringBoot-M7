package com.example.authservice.infrastructure.adapter.persistence.mapper;

import com.example.authservice.domain.model.User;
import com.example.authservice.infrastructure.adapter.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain entity and JPA entity
 */
@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        
        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        user.setName(entity.getName());
        user.setRole(entity.getRole());
        user.setCreatedAt(entity.getCreatedAt());
        user.setActive(entity.isActive());
        
        return user;
    }

    public UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }
        
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setName(user.getName());
        entity.setRole(user.getRole());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setActive(user.isActive());
        
        return entity;
    }
}
