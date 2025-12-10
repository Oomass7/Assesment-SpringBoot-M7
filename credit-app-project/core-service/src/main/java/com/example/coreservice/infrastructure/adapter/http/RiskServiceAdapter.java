package com.example.coreservice.infrastructure.adapter.http;

import com.example.coreservice.application.port.out.RiskServicePort;
import com.example.coreservice.domain.model.RiskEvaluation;
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
    public RiskEvaluation evaluate(String document, Double amount, Integer term) {
        try {
            String url = riskServiceUrl + "/evaluate";
            
            Map<String, Object> request = new HashMap<>();
            request.put("document", document);
            request.put("amount", amount);
            request.put("term", term);
            
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            
            if (response != null) {
                return new RiskEvaluation(
                    String.valueOf(response.get("document")),
                    (Integer) response.get("score"),
                    (String) response.get("riskLevel")
                );
            }
            
            throw new RuntimeException("Respuesta vacía del servicio de riesgo");
        } catch (Exception e) {
            System.err.println("Error al comunicarse con risk-service: " + e.getMessage());
            throw new RuntimeException("No se pudo evaluar el riesgo", e);
        }
    }
}
