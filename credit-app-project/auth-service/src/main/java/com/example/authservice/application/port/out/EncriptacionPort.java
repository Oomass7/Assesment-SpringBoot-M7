package com.example.authservice.application.port.out;

/**
 * Puerto de salida - Interfaz para encriptación de contraseñas
 */
public interface EncriptacionPort {
    String encriptar(String textoPlano);
    boolean verificar(String textoPlano, String textoEncriptado);
}
