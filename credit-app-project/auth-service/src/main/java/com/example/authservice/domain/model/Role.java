package com.example.authservice.domain.model;

/**
 * Enum for user roles in the system
 */
public enum Role {
    AFILIADO,   // Affiliate - can only see their own requests
    ANALISTA,   // Analyst - can see PENDING requests
    ADMIN;      // Admin - full access

    public static boolean isValid(String role) {
        if (role == null) return false;
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static Role fromString(String role) {
        if (role == null) return AFILIADO;
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return AFILIADO; // Default role
        }
    }
}
