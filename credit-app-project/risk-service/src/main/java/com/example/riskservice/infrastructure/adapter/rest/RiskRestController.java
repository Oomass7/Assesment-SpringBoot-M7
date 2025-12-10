package com.example.riskservice.infrastructure.adapter.rest;

import com.example.riskservice.application.port.in.EvaluateRiskUseCase;
import com.example.riskservice.domain.model.Evaluation;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluationRequest;
import com.example.riskservice.infrastructure.adapter.rest.dto.EvaluationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST - Controlador para evaluación de riesgo
 */
@RestController
@Tag(name = "Risk Evaluation", description = "API de evaluación de riesgo crediticio")
@SecurityRequirement(name = "Bearer Authentication")
public class RiskRestController {

    private final EvaluateRiskUseCase evaluarRiesgoUseCase;

    public RiskRestController(EvaluateRiskUseCase evaluarRiesgoUseCase) {
        this.evaluarRiesgoUseCase = evaluarRiesgoUseCase;
    }

    @PostMapping("/evaluate")
    @Operation(
        summary = "Evaluar riesgo crediticio",
        description = "Calcula el score de riesgo basado en documento, monto y plazo del cliente. " +
                      "Retorna score numérico y nivel de riesgo (BAJO, MEDIO, ALTO)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Evaluación completada exitosamente",
            content = @Content(schema = @Schema(implementation = EvaluationResponse.class))
        ),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
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
    @Operation(
        summary = "Health check",
        description = "Verifica que el servicio de riesgo esté funcionando correctamente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Servicio funcionando correctamente"
        )
    })
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Risk Service is running");
    }
}
