package com.example.coreservice.infrastructure.adapter.rest;

import com.example.coreservice.application.port.in.ConsultarSolicitudUseCase;
import com.example.coreservice.application.port.in.CrearSolicitudUseCase;
import com.example.coreservice.domain.model.SolicitudCredito;
import com.example.coreservice.infrastructure.adapter.rest.dto.SolicitudRequest;
import com.example.coreservice.infrastructure.adapter.rest.dto.SolicitudResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador REST - Controlador para solicitudes
 */
@RestController
@RequestMapping("/solicitudes")
public class SolicitudRestController {

    private final CrearSolicitudUseCase crearSolicitudUseCase;
    private final ConsultarSolicitudUseCase consultarSolicitudUseCase;

    public SolicitudRestController(
            CrearSolicitudUseCase crearSolicitudUseCase,
            ConsultarSolicitudUseCase consultarSolicitudUseCase) {
        this.crearSolicitudUseCase = crearSolicitudUseCase;
        this.consultarSolicitudUseCase = consultarSolicitudUseCase;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@RequestBody SolicitudRequest request) {
        // Convertir DTO a modelo de dominio
        SolicitudCredito solicitud = new SolicitudCredito(
            request.getDocumentoCliente(),
            request.getNombreCliente(),
            request.getMontoSolicitado(),
            request.getPlazoMeses()
        );

        // Ejecutar caso de uso
        SolicitudCredito solicitudCreada = crearSolicitudUseCase.crear(solicitud);

        // Convertir a DTO de respuesta
        SolicitudResponse response = toResponse(solicitudCreada);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> buscarPorId(@PathVariable Long id) {
        SolicitudCredito solicitud = consultarSolicitudUseCase.buscarPorId(id);
        return ResponseEntity.ok(toResponse(solicitud));
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> listarTodas() {
        List<SolicitudCredito> solicitudes = consultarSolicitudUseCase.listarTodas();
        List<SolicitudResponse> response = solicitudes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/documento/{documento}")
    public ResponseEntity<List<SolicitudResponse>> buscarPorDocumento(@PathVariable String documento) {
        List<SolicitudCredito> solicitudes = consultarSolicitudUseCase.buscarPorDocumento(documento);
        List<SolicitudResponse> response = solicitudes.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private SolicitudResponse toResponse(SolicitudCredito solicitud) {
        SolicitudResponse response = new SolicitudResponse();
        response.setId(solicitud.getId());
        response.setDocumentoCliente(solicitud.getDocumentoCliente());
        response.setNombreCliente(solicitud.getNombreCliente());
        response.setMontoSolicitado(solicitud.getMontoSolicitado());
        response.setPlazoMeses(solicitud.getPlazoMeses());
        response.setEstado(solicitud.getEstado());
        response.setScoreRiesgo(solicitud.getScoreRiesgo());
        response.setNivelRiesgo(solicitud.getNivelRiesgo());
        response.setFechaSolicitud(solicitud.getFechaSolicitud());
        response.setFechaEvaluacion(solicitud.getFechaEvaluacion());
        return response;
    }
}
