package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.SolicitudCredito;
import java.util.List;

/**
 * Puerto de entrada - Caso de uso para consultar solicitudes
 */
public interface ConsultarSolicitudUseCase {
    SolicitudCredito buscarPorId(Long id);
    List<SolicitudCredito> listarTodas();
    List<SolicitudCredito> buscarPorDocumento(String documento);
}
