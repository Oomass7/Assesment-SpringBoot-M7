package com.example.authservice.application.port.in;

import com.example.authservice.domain.model.Usuario;

/**
 * Puerto de entrada - Caso de uso para registrar usuarios
 */
public interface RegistrarUsuarioUseCase {
    Usuario registrar(Usuario usuario);
}
