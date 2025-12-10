package com.example.authservice.application.port.in;

import com.example.authservice.domain.model.User;

/**
 * Puerto de entrada - Caso de uso para registrar users
 */
public interface RegisterUserUseCase {
    User register(User user);
}
