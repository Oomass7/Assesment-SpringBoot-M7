package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.SolicitudCredito;

/**
 * Puerto de entrada - Caso de uso para crear solicitudes
 */
public interface CrearSolicitudUseCase {
    SolicitudCredito crear(SolicitudCredito solicitud);
}
