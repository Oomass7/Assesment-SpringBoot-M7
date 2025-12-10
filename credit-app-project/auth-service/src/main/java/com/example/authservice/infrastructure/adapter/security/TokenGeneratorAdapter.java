package com.example.authservice.infrastructure.adapter.security;

import com.example.authservice.application.port.out.TokenGeneratorPort;
import com.example.authservice.domain.model.TokenAuth;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * Adapter for generating real JWT tokens
 */
@Component
public class TokenGeneratorAdapter implements TokenGeneratorPort {

    private final JwtService jwtService;

    public TokenGeneratorAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public TokenAuth generate(com.example.authservice.domain.model.User user) {
        // Create UserDetails to generate JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + user.getRole())
                ))
                .build();

        // Generate real JWT token with signature
        String token = jwtService.generateToken(userDetails);
        
        // Expiration is 24 hours (configured in JwtService)
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
        
        return new TokenAuth(token, "Bearer", expiresAt);
    }

    @Override
    public boolean validate(String token) {
        try {
            // Extract email from token to validate
            String email = jwtService.extractUsername(token);
            return email != null && !email.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
