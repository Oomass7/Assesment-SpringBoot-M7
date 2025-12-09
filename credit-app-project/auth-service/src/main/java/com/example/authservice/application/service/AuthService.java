package com.example.authservice.application.service;

import com.example.authservice.application.port.in.AutenticarUsuarioUseCase;
import com.example.authservice.application.port.in.RegistrarUsuarioUseCase;
import com.example.authservice.application.port.out.EncriptacionPort;
import com.example.authservice.application.port.out.TokenGeneratorPort;
import com.example.authservice.application.port.out.UsuarioRepositoryPort;
import com.example.authservice.domain.exception.CredencialesInvalidasException;
import com.example.authservice.domain.exception.UsuarioNoEncontradoException;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.Usuario;
import org.springframework.stereotype.Service;

/**
 * Servicio de aplicación que implementa los casos de uso
 * Esta es la capa de aplicación en arquitectura hexagonal
 */
@Service
public class AuthService implements RegistrarUsuarioUseCase, AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final EncriptacionPort encriptacion;
    private final TokenGeneratorPort tokenGenerator;

    public AuthService(
            UsuarioRepositoryPort usuarioRepository,
            EncriptacionPort encriptacion,
            TokenGeneratorPort tokenGenerator) {
        this.usuarioRepository = usuarioRepository;
        this.encriptacion = encriptacion;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        // Validaciones de negocio
        if (!usuario.esValido()) {
            throw new IllegalArgumentException("Datos de usuario inválidos");
        }

        if (usuarioRepository.existePorEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Encriptar contraseña
        String passwordEncriptada = encriptacion.encriptar(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        // Guardar usuario
        return usuarioRepository.guardar(usuario);
    }

    @Override
    public TokenAuth autenticar(String email, String password) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.buscarPorEmail(email)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        // Verificar contraseña
        if (!encriptacion.verificar(password, usuario.getPassword())) {
            throw new CredencialesInvalidasException("Credenciales inválidas");
        }

        // Verificar que el usuario esté activo
        if (!usuario.isActivo()) {
            throw new CredencialesInvalidasException("Usuario inactivo");
        }

        // Generar token
        return tokenGenerator.generar(usuario);
    }
}
