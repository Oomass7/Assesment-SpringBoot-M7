package com.example.authservice.infrastructure.adapter.rest;

import com.example.authservice.application.port.in.AuthenticateUserUseCase;
import com.example.authservice.application.port.in.RegisterUserUseCase;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.User;
import com.example.authservice.infrastructure.adapter.rest.dto.AuthResponse;
import com.example.authservice.infrastructure.adapter.rest.dto.LoginRequest;
import com.example.authservice.infrastructure.adapter.rest.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST - Controlador para autenticación
 * Puerto de entrada HTTP
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API de autenticación y registro de usuarios")
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
    @Operation(summary = "Registrar nuevo usuario", description = "Crea un nuevo usuario en el sistema con encriptación de contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o email ya registrado")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Convert DTO to domain model
        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getName(),
                request.getRole());

        // Execute use case
        User registeredUser = registrarUsuarioUseCase.register(user);

        // Respond
        AuthResponse response = new AuthResponse(
                null,
                null,
                "User registered successfully with ID: " + registeredUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT para acceder a los servicios protegidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // Execute use case
        TokenAuth token = autenticarUsuarioUseCase.authenticate(
                request.getEmail(),
                request.getPassword());

        // Respond
        AuthResponse response = new AuthResponse(
                token.getToken(),
                token.getType(),
                "Authentication successful");

        return ResponseEntity.ok(response);
    }
}
