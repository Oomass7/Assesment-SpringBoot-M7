package com.example.authservice.application.service;

import com.example.authservice.application.port.in.AuthenticateUserUseCase;
import com.example.authservice.application.port.in.RegisterUserUseCase;
import com.example.authservice.application.port.out.EncryptionPort;
import com.example.authservice.application.port.out.TokenGeneratorPort;
import com.example.authservice.application.port.out.UserRepositoryPort;
import com.example.authservice.domain.exception.InvalidCredentialsException;
import com.example.authservice.domain.exception.UserNotFoundException;
import com.example.authservice.domain.model.Role;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.User;
import org.springframework.stereotype.Service;

/**
 * Application service implementing use cases
 * This is the application layer in hexagonal architecture
 */
@Service
public class AuthService implements RegisterUserUseCase, AuthenticateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final EncryptionPort encryption;
    private final TokenGeneratorPort tokenGenerator;

    public AuthService(
            UserRepositoryPort userRepository,
            EncryptionPort encryption,
            TokenGeneratorPort tokenGenerator) {
        this.userRepository = userRepository;
        this.encryption = encryption;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public User register(User user) {
        // Business validations
        if (!user.isValid()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Validate role
        if (!Role.isValid(user.getRole())) {
            throw new IllegalArgumentException("Invalid role. Valid roles are: AFILIADO, ANALISTA, ADMIN");
        }

        // Normalize role to uppercase
        user.setRole(user.getRole().toUpperCase());

        // Encrypt password
        String encryptedPassword = encryption.encrypt(user.getPassword());
        user.setPassword(encryptedPassword);

        // Save user
        return userRepository.save(user);
    }

    @Override
    public TokenAuth authenticate(String email, String password) {
        // Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Verify password
        if (!encryption.verify(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        // Verify user is active
        if (!user.isActive()) {
            throw new InvalidCredentialsException("User is inactive");
        }

        // Generate token
        return tokenGenerator.generate(user);
    }
}
