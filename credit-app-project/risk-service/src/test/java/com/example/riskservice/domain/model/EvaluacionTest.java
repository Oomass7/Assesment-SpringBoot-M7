package com.example.riskservice.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests del modelo Evaluacion")
class EvaluacionTest {

    @Test
    @DisplayName("Debe crear evaluación y calcular riesgo")
    void debeCrearEvaluacionYCalcularRiesgo() {
        Evaluacion evaluacion = new Evaluacion("12345678", 30000.0, 24);
        evaluacion.calcularRiesgo();
        
        assertNotNull(evaluacion);
        assertNotNull(evaluacion.getScore());
        assertTrue(evaluacion.getScore() >= 300 && evaluacion.getScore() <= 950);
        assertNotNull(evaluacion.getNivelRiesgo());
    }

    @Test
    @DisplayName("Debe clasificar correctamente el nivel de riesgo")
    void debeClasificarNivelRiesgo() {
        Evaluacion evaluacion = new Evaluacion("99999999", 100000.0, 60);
        evaluacion.calcularRiesgo();
        
        assertNotNull(evaluacion.getNivelRiesgo());
        assertTrue(evaluacion.getNivelRiesgo().equals("BAJO") || 
                   evaluacion.getNivelRiesgo().equals("MEDIO") || 
                   evaluacion.getNivelRiesgo().equals("ALTO"));
    }
}
