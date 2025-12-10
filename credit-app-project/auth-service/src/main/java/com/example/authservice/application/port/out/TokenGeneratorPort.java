package com.example.authservice.application.port.out;

import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.User;

/**
 * Output port - Interface for token generation
 */
public interface TokenGeneratorPort {
    TokenAuth generate(User user);
    boolean validate(String token);
}
