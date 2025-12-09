package com.example.coreservice.infrastructure.adapter.http;

import com.example.coreservice.application.port.out.RiskServicePort;
import com.example.coreservice.domain.model.EvaluacionRiesgo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Adaptador HTTP para comunicación con Risk Service
 */
@Component
public class RiskServiceAdapter implements RiskServicePort {

    private final RestTemplate restTemplate;
    
    @Value("${risk.service.url:http://risk-service:8083}")
    private String riskServiceUrl;

    public RiskServiceAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public EvaluacionRiesgo evaluar(String documento, Double monto, Integer plazo) {
        try {
            String url = riskServiceUrl + "/evaluate";
            
            Map<String, Object> request = new HashMap<>();
            request.put("documento", documento);
            request.put("monto", monto);
            request.put("plazo", plazo);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            
            if (response != null) {
                return new EvaluacionRiesgo(
                    String.valueOf(response.get("documento")),
                    (Integer) response.get("score"),
                    (String) response.get("nivelRiesgo")
                );
            }
            
            throw new RuntimeException("Respuesta vacía del servicio de riesgo");
        } catch (Exception e) {
            System.err.println("Error al comunicarse con risk-service: " + e.getMessage());
            throw new RuntimeException("No se pudo evaluar el riesgo", e);
        }
    }
}
