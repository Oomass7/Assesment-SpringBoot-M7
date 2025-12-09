package com.example.authservice.infrastructure.adapter.rest.dto;

/**
 * DTO para respuesta de autenticación
 */
public class AuthResponse {
    private String token;
    private String tipo;
    private String mensaje;

    // Constructores
    public AuthResponse() {
    }

    public AuthResponse(String token, String tipo, String mensaje) {
        this.token = token;
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
