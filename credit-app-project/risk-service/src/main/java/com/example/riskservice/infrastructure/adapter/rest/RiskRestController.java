package com.example.riskservice.infrastructure.adapter.rest;

import com.example.riskservice.application.port.in.EvaluarRiesgoUseCase;
import com.example.riskservice.domain.model.Evaluacion;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluacionRequest;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluacionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST - Controlador para evaluación de riesgo
 */
@RestController
public class RiskRestController {

    private final EvaluarRiesgoUseCase evaluarRiesgoUseCase;

    public RiskRestController(EvaluarRiesgoUseCase evaluarRiesgoUseCase) {
        this.evaluarRiesgoUseCase = evaluarRiesgoUseCase;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<EvaluacionResponse> evaluar(@RequestBody EvaluacionRequest request) {
        // Ejecutar caso de uso
        Evaluacion evaluacion = evaluarRiesgoUseCase.evaluar(
            request.getDocumento(),
            request.getMonto(),
            request.getPlazo()
        );

        // Convertir a DTO de respuesta
        EvaluacionResponse response = new EvaluacionResponse(
            evaluacion.getDocumento(),
            evaluacion.getScore(),
            evaluacion.getNivelRiesgo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Risk Service is running");
    }
}
