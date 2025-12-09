package com.example.authservice.application.port.in;

import com.example.authservice.domain.model.TokenAuth;

/**
 * Puerto de entrada - Caso de uso para autenticar usuarios
 */
public interface AutenticarUsuarioUseCase {
    TokenAuth autenticar(String email, String password);
}
