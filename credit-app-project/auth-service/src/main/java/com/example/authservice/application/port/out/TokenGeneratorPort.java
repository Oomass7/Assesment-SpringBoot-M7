package com.example.authservice.application.port.out;

import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.Usuario;

/**
 * Puerto de salida - Interfaz para generación de tokens
 */
public interface TokenGeneratorPort {
    TokenAuth generar(Usuario usuario);
    boolean validar(String token);
}
