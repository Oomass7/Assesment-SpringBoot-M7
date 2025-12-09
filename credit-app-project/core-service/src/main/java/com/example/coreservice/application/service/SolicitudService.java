package com.example.coreservice.application.service;

import com.example.coreservice.application.port.in.ConsultarSolicitudUseCase;
import com.example.coreservice.application.port.in.CrearSolicitudUseCase;
import com.example.coreservice.application.port.out.RiskServicePort;
import com.example.coreservice.application.port.out.SolicitudRepositoryPort;
import com.example.coreservice.domain.exception.SolicitudNoEncontradaException;
import com.example.coreservice.domain.model.EvaluacionRiesgo;
import com.example.coreservice.domain.model.SolicitudCredito;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de aplicación para solicitudes de crédito
 */
@Service
public class SolicitudService implements CrearSolicitudUseCase, ConsultarSolicitudUseCase {

    private final SolicitudRepositoryPort solicitudRepository;
    private final RiskServicePort riskService;

    public SolicitudService(
            SolicitudRepositoryPort solicitudRepository,
            RiskServicePort riskService) {
        this.solicitudRepository = solicitudRepository;
        this.riskService = riskService;
    }

    @Override
    public SolicitudCredito crear(SolicitudCredito solicitud) {
        // Validar solicitud
        if (!solicitud.esValida()) {
            throw new IllegalArgumentException("Datos de solicitud inválidos");
        }

        // Guardar solicitud inicial
        SolicitudCredito solicitudGuardada = solicitudRepository.guardar(solicitud);

        // Evaluar riesgo llamando al microservicio de riesgo
        try {
            EvaluacionRiesgo evaluacion = riskService.evaluar(
                solicitud.getDocumentoCliente(),
                solicitud.getMontoSolicitado(),
                solicitud.getPlazoMeses()
            );

            // Asignar evaluación de riesgo
            solicitudGuardada.asignarEvaluacionRiesgo(
                evaluacion.getScore(),
                evaluacion.getNivelRiesgo()
            );

            // Actualizar solicitud con evaluación
            return solicitudRepository.guardar(solicitudGuardada);
        } catch (Exception e) {
            // Si falla la evaluación, mantener como PENDIENTE
            System.err.println("Error al evaluar riesgo: " + e.getMessage());
            return solicitudGuardada;
        }
    }

    @Override
    public SolicitudCredito buscarPorId(Long id) {
        return solicitudRepository.buscarPorId(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException("Solicitud no encontrada con ID: " + id));
    }

    @Override
    public List<SolicitudCredito> listarTodas() {
        return solicitudRepository.buscarTodas();
    }

    @Override
    public List<SolicitudCredito> buscarPorDocumento(String documento) {
        return solicitudRepository.buscarPorDocumento(documento);
    }
}
