package com.example.authservice.domain.model;

import java.time.LocalDateTime;

/**
 * Value Object to represent an authentication token
 */
public class TokenAuth {
    private final String token;
    private final String type;
    private final LocalDateTime expiresAt;

    public TokenAuth(String token, String type, LocalDateTime expiresAt) {
        this.token = token;
        this.type = type;
        this.expiresAt = expiresAt;
    }

    public boolean isValid() {
        return expiresAt.isAfter(LocalDateTime.now());
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
