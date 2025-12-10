package com.example.authservice.application.port.in;

import com.example.authservice.domain.model.TokenAuth;

/**
 * Puerto de entrada - Caso de uso para autenticar users
 */
public interface AuthenticateUserUseCase {
    TokenAuth authenticate(String email, String password);
}
