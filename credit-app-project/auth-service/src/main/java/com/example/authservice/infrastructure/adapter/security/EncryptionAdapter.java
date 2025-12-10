package com.example.authservice.infrastructure.adapter.security;

import com.example.authservice.application.port.out.EncryptionPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Adaptador de seguridad para encriptación con BCrypt
 */
@Component
public class EncryptionAdapter implements EncryptionPort {

    private final PasswordEncoder passwordEncoder;

    public EncryptionAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    @Override
    public boolean verify(String plainText, String encryptedText) {
        return passwordEncoder.matches(plainText, encryptedText);
    }
}
