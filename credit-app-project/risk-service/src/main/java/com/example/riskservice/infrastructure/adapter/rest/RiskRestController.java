package com.example.riskservice.infrastructure.adapter.rest;

import com.example.riskservice.application.port.in.EvaluateRiskUseCase;
import com.example.riskservice.domain.model.Evaluation;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluationRequest;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST - Controlador para evaluación de riesgo
 */
@RestController
public class RiskRestController {

    private final EvaluateRiskUseCase evaluarRiesgoUseCase;

    public RiskRestController(EvaluateRiskUseCase evaluarRiesgoUseCase) {
        this.evaluarRiesgoUseCase = evaluarRiesgoUseCase;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResponse> evaluate(@RequestBody EvaluationRequest request) {
        // Ejecutar caso de uso
        Evaluation evaluation = evaluarRiesgoUseCase.evaluate(
            request.getClientDocument(),
            request.getRequestedAmount(),
            request.getTermMonths()
        );

        // Convertir a DTO de respuesta
        EvaluationResponse response = new EvaluationResponse(
            evaluation.getClientDocument(),
            evaluation.getScore(),
            evaluation.getRiskLevel()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Risk Service is running");
    }
}
