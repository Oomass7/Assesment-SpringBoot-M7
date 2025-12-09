package com.example.authservice.domain.model;

import java.time.LocalDateTime;

/**
 * Value Object para representar un token de autenticación
 */
public class TokenAuth {
    private final String token;
    private final String tipo;
    private final LocalDateTime fechaExpiracion;

    public TokenAuth(String token, String tipo, LocalDateTime fechaExpiracion) {
        this.token = token;
        this.tipo = tipo;
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean esValido() {
        return fechaExpiracion.isAfter(LocalDateTime.now());
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
}
