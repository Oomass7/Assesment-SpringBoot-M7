package com.example.authservice.infrastructure.adapter.security;

import com.example.authservice.application.port.out.TokenGeneratorPort;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * Adaptador para generación de tokens
 * NOTA: En producción se debe usar JWT con firma y validación
 */
@Component
public class TokenGeneratorAdapter implements TokenGeneratorPort {

    @Override
    public TokenAuth generar(Usuario usuario) {
        // Generar token simple (en producción usar JWT)
        String token = Base64.getEncoder().encodeToString(
            (usuario.getEmail() + ":" + UUID.randomUUID()).getBytes()
        );
        
        LocalDateTime expiracion = LocalDateTime.now().plusHours(24);
        
        return new TokenAuth(token, "Bearer", expiracion);
    }

    @Override
    public boolean validar(String token) {
        // Validación simple (en producción validar firma JWT)
        return token != null && !token.isEmpty();
    }
}
