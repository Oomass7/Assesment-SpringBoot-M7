package com.example.authservice.infrastructure.adapter.persistence.mapper;

import com.example.authservice.domain.model.Usuario;
import com.example.authservice.infrastructure.adapter.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entidad de dominio y entidad JPA
 */
@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setEmail(entity.getEmail());
        usuario.setPassword(entity.getPassword());
        usuario.setNombre(entity.getNombre());
        usuario.setRol(entity.getRol());
        usuario.setFechaCreacion(entity.getFechaCreacion());
        usuario.setActivo(entity.isActivo());
        
        return usuario;
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setEmail(usuario.getEmail());
        entity.setPassword(usuario.getPassword());
        entity.setNombre(usuario.getNombre());
        entity.setRol(usuario.getRol());
        entity.setFechaCreacion(usuario.getFechaCreacion());
        entity.setActivo(usuario.isActivo());
        
        return entity;
    }
}
