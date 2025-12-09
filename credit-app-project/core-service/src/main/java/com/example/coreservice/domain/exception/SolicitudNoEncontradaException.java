package com.example.coreservice.domain.exception;

public class SolicitudNoEncontradaException extends RuntimeException {
    public SolicitudNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
