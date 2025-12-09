package com.example.authservice.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests del modelo Usuario")
class UsuarioTest {

    @Test
    @DisplayName("Debe crear un usuario válido")
    void debeCrearUsuarioValido() {
        Usuario usuario = new Usuario("test@example.com", "password123", "Juan Pérez", "USER");
        
        assertNotNull(usuario);
        assertEquals("test@example.com", usuario.getEmail());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertTrue(usuario.isActivo());
    }

    @Test
    @DisplayName("Debe actualizar email del usuario")
    void debeActualizarEmail() {
        Usuario usuario = new Usuario("test@example.com", "pass123", "Juan", "USER");
        usuario.setEmail("nuevo@example.com");
        
        assertEquals("nuevo@example.com", usuario.getEmail());
    }
}
