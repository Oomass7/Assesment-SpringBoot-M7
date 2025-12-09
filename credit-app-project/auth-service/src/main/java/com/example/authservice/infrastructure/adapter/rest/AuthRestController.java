package com.example.authservice.infrastructure.adapter.rest;

import com.example.authservice.application.port.in.AutenticarUsuarioUseCase;
import com.example.authservice.application.port.in.RegistrarUsuarioUseCase;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.Usuario;
import com.example.authservice.infrastructure.adapter.rest.dto.AuthResponse;
import com.example.authservice.infrastructure.adapter.rest.dto.LoginRequest;
import com.example.authservice.infrastructure.adapter.rest.dto.RegistroRequest;
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

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthRestController(
            RegistrarUsuarioUseCase registrarUsuarioUseCase,
            AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(@RequestBody RegistroRequest request) {
        // Convertir DTO a modelo de dominio
        Usuario usuario = new Usuario(
            request.getEmail(),
            request.getPassword(),
            request.getNombre(),
            request.getRol()
        );

        // Ejecutar caso de uso
        Usuario usuarioRegistrado = registrarUsuarioUseCase.registrar(usuario);

        // Responder
        AuthResponse response = new AuthResponse(
            null,
            null,
            "Usuario registrado exitosamente con ID: " + usuarioRegistrado.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Ejecutar caso de uso
        TokenAuth token = autenticarUsuarioUseCase.autenticar(
            request.getEmail(),
            request.getPassword()
        );

        // Responder
        AuthResponse response = new AuthResponse(
            token.getToken(),
            token.getTipo(),
            "Autenticación exitosa"
        );

        return ResponseEntity.ok(response);
    }
}
