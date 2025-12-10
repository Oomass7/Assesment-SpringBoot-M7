package com.example.authservice.infrastructure.adapter.rest.dto;

/**
 * DTO for authentication response
 */
public class AuthResponse {
    private String token;
    private String type;
    private String message;

    // Constructors
    public AuthResponse() {
    }

    public AuthResponse(String token, String type, String message) {
        this.token = token;
        this.type = type;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
