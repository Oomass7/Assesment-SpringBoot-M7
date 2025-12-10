package com.example.coreservice.application.service;

import com.example.coreservice.application.port.in.GetRequestUseCase;
import com.example.coreservice.application.port.in.CreateRequestUseCase;
import com.example.coreservice.application.port.out.RiskServicePort;
import com.example.coreservice.application.port.out.RequestRepositoryPort;
import com.example.coreservice.domain.exception.RequestNotFoundException;
import com.example.coreservice.domain.model.RiskEvaluation;
import com.example.coreservice.domain.model.CreditRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for credit requests
 */
@Service
public class RequestService implements CreateRequestUseCase, GetRequestUseCase {

    private final RequestRepositoryPort requestRepository;
    private final RiskServicePort riskService;

    public RequestService(
            RequestRepositoryPort requestRepository,
            RiskServicePort riskService) {
        this.requestRepository = requestRepository;
        this.riskService = riskService;
    }

    @Override
    public CreditRequest create(CreditRequest request) {
        // Validate request
        if (!request.isValid()) {
            throw new IllegalArgumentException("Invalid request data");
        }

        // Save initial request
        CreditRequest savedRequest = requestRepository.save(request);

        // Evaluate risk by calling risk microservice
        try {
            RiskEvaluation evaluation = riskService.evaluate(
                    request.getClientDocument(),
                    request.getRequestedAmount(),
                    request.getTermMonths());

            // Assign risk evaluation
            savedRequest.assignRiskEvaluation(
                    evaluation.getScore(),
                    evaluation.getRiskLevel());

            // Update request with evaluation
            return requestRepository.save(savedRequest);
        } catch (Exception e) {
            // If evaluation fails, keep as PENDING
            System.err.println("Error evaluating risk: " + e.getMessage());
            return savedRequest;
        }
    }

    @Override
    public CreditRequest findById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with ID: " + id));
    }

    @Override
    public List<CreditRequest> getAll() {
        return requestRepository.buscarTodas();
    }

    @Override
    public List<CreditRequest> findByDocument(String document) {
        return requestRepository.findByDocument(document);
    }

    @Override
    public List<CreditRequest> findByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public List<CreditRequest> findByUserEmail(String userEmail) {
        // For affiliates, we use the document as the identifier
        // In a real system, you would have a user-document mapping
        return requestRepository.findByDocument(userEmail);
    }
}
