package com.example.authservice.infrastructure.adapter.rest;

import com.example.authservice.application.port.in.AuthenticateUserUseCase;
import com.example.authservice.application.port.in.RegisterUserUseCase;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.User;
import com.example.authservice.infrastructure.adapter.rest.dto.AuthResponse;
import com.example.authservice.infrastructure.adapter.rest.dto.LoginRequest;
import com.example.authservice.infrastructure.adapter.rest.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST - Controlador para autenticación
 * Puerto de entrada HTTP
 */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final RegisterUserUseCase registrarUsuarioUseCase;
    private final AuthenticateUserUseCase autenticarUsuarioUseCase;

    public AuthRestController(
            RegisterUserUseCase registrarUsuarioUseCase,
            AuthenticateUserUseCase autenticarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Convertir DTO a modelo de dominio
        User user = new User(
            request.getEmail(),
            request.getPassword(),
            request.getName(),
            request.getRole()
        );

        // Ejecutar caso de uso
        User userRegistrado = registrarUsuarioUseCase.register(user);

        // Responder
        AuthResponse response = new AuthResponse(
            null,
            null,
            "User registrado exitosamente con ID: " + userRegistrado.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Ejecutar caso de uso
        TokenAuth token = autenticarUsuarioUseCase.authenticate(
            request.getEmail(),
            request.getPassword()
        );

        // Responder
        AuthResponse response = new AuthResponse(
            token.getToken(),
            token.getType(),
            "Autenticación exitosa"
        );

        return ResponseEntity.ok(response);
    }
}
