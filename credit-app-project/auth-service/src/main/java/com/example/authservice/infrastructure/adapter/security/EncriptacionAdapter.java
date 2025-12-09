package com.example.authservice.infrastructure.adapter.security;

import com.example.authservice.application.port.out.EncriptacionPort;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Adaptador de seguridad para encriptación
 * NOTA: En producción se debe usar BCrypt o Argon2
 */
@Component
public class EncriptacionAdapter implements EncriptacionPort {

    @Override
    public String encriptar(String textoPlano) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(textoPlano.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }

    @Override
    public boolean verificar(String textoPlano, String textoEncriptado) {
        String encriptado = encriptar(textoPlano);
        return encriptado.equals(textoEncriptado);
    }
}
