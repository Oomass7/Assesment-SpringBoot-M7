package com.example.authservice.application.port.out;

import com.example.authservice.domain.model.Usuario;
import java.util.Optional;

/**
 * Puerto de salida - Interfaz para persistencia de usuarios
 */
public interface UsuarioRepositoryPort {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    boolean existePorEmail(String email);
}
