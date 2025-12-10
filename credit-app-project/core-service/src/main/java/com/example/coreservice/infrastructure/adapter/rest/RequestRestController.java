package com.example.coreservice.infrastructure.adapter.rest;

import com.example.coreservice.application.port.in.GetRequestUseCase;
import com.example.coreservice.application.port.in.CreateRequestUseCase;
import com.example.coreservice.domain.model.CreditRequest;
import com.example.coreservice.infrastructure.adapter.rest.dto.RequestDTO;
import com.example.coreservice.infrastructure.adapter.rest.dto.RequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Adapter - Controller for credit requests with role-based access control
 */
@RestController
@RequestMapping("/requests")
public class RequestRestController {

    private final CreateRequestUseCase createRequestUseCase;
    private final GetRequestUseCase getRequestUseCase;

    public RequestRestController(
            CreateRequestUseCase createRequestUseCase,
            GetRequestUseCase getRequestUseCase) {
        this.createRequestUseCase = createRequestUseCase;
        this.getRequestUseCase = getRequestUseCase;
    }

    /**
     * Create a new credit request
     * Accessible by: AFILIADO, ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('AFILIADO', 'ADMIN')")
    public ResponseEntity<RequestResponse> create(@RequestBody RequestDTO requestDTO) {
        CreditRequest request = new CreditRequest(
                requestDTO.getClientDocument(),
                requestDTO.getClientName(),
                requestDTO.getRequestedAmount(),
                requestDTO.getTermMonths());

        CreditRequest createdRequest = createRequestUseCase.create(request);
        RequestResponse response = toResponse(createdRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all requests
     * Accessible by: ADMIN only
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RequestResponse>> getAll() {
        List<CreditRequest> requests = getRequestUseCase.getAll();
        List<RequestResponse> response = requests.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Get request by ID
     * Accessible by: AFILIADO (own requests), ANALISTA, ADMIN
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    public ResponseEntity<RequestResponse> findById(@PathVariable Long id) {
        CreditRequest request = getRequestUseCase.findById(id);

        // If user is AFILIADO, verify ownership
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAffiliate = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AFILIADO"));

        if (isAffiliate) {
            String userEmail = auth.getName();
            // Check if the request belongs to the user (using document as identifier)
            if (!request.getClientDocument().equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok(toResponse(request));
    }

    /**
     * Get requests by document
     * Accessible by: AFILIADO (own requests), ADMIN
     */
    @GetMapping("/document/{document}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ADMIN')")
    public ResponseEntity<List<RequestResponse>> findByDocument(@PathVariable String document) {
        // If user is AFILIADO, they can only see their own requests
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAffiliate = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_AFILIADO"));

        if (isAffiliate) {
            String userEmail = auth.getName();
            if (!document.equals(userEmail)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        List<CreditRequest> requests = getRequestUseCase.findByDocument(document);
        List<RequestResponse> response = requests.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Get all pending requests (for analysts to review)
     * Accessible by: ANALISTA, ADMIN
     */
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ANALISTA', 'ADMIN')")
    public ResponseEntity<List<RequestResponse>> getPendingRequests() {
        List<CreditRequest> requests = getRequestUseCase.findByStatus("PENDIENTE");
        List<RequestResponse> response = requests.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Get my requests (for affiliates)
     * Accessible by: AFILIADO only
     */
    @GetMapping("/my-requests")
    @PreAuthorize("hasRole('AFILIADO')")
    public ResponseEntity<List<RequestResponse>> getMyRequests() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        List<CreditRequest> requests = getRequestUseCase.findByUserEmail(userEmail);
        List<RequestResponse> response = requests.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private RequestResponse toResponse(CreditRequest request) {
        RequestResponse response = new RequestResponse();
        response.setId(request.getId());
        response.setClientDocument(request.getClientDocument());
        response.setClientName(request.getClientName());
        response.setRequestedAmount(request.getRequestedAmount());
        response.setTermMonths(request.getTermMonths());
        response.setStatus(request.getStatus());
        response.setRiskScore(request.getRiskScore());
        response.setRiskLevel(request.getRiskLevel());
        response.setRequestDate(request.getRequestDate());
        response.setEvaluationDate(request.getEvaluationDate());
        return response;
    }
}
