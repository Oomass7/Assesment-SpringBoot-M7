package com.example.coreservice.application.port.out;

import com.example.coreservice.domain.model.SolicitudCredito;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida - Interfaz para persistencia de solicitudes
 */
public interface SolicitudRepositoryPort {
    SolicitudCredito guardar(SolicitudCredito solicitud);
    Optional<SolicitudCredito> buscarPorId(Long id);
    List<SolicitudCredito> buscarTodas();
    List<SolicitudCredito> buscarPorDocumento(String documento);
}
