package com.example.coreservice.infrastructure.adapter.rest;

import com.example.coreservice.application.port.in.GetAffiliateUseCase;
import com.example.coreservice.application.port.in.RegisterAffiliateUseCase;
import com.example.coreservice.application.port.in.UpdateAffiliateUseCase;
import com.example.coreservice.domain.model.Affiliate;
import com.example.coreservice.infrastructure.adapter.rest.dto.AffiliateDTO;
import com.example.coreservice.infrastructure.adapter.rest.dto.AffiliateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for affiliate management
 */
@RestController
@RequestMapping("/affiliates")
public class AffiliateRestController {

    private final RegisterAffiliateUseCase registerAffiliateUseCase;
    private final GetAffiliateUseCase getAffiliateUseCase;
    private final UpdateAffiliateUseCase updateAffiliateUseCase;

    public AffiliateRestController(
            RegisterAffiliateUseCase registerAffiliateUseCase,
            GetAffiliateUseCase getAffiliateUseCase,
            UpdateAffiliateUseCase updateAffiliateUseCase) {
        this.registerAffiliateUseCase = registerAffiliateUseCase;
        this.getAffiliateUseCase = getAffiliateUseCase;
        this.updateAffiliateUseCase = updateAffiliateUseCase;
    }

    /**
     * Register a new affiliate
     * Accessible by: ADMIN only
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AffiliateResponse> register(@Valid @RequestBody AffiliateDTO dto) {
        Affiliate affiliate = new Affiliate(
                dto.getDocument(),
                dto.getName(),
                dto.getSalary());

        Affiliate created = registerAffiliateUseCase.register(affiliate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }

    /**
     * Get all affiliates
     * Accessible by: ADMIN, ANALISTA
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<AffiliateResponse>> getAll() {
        List<Affiliate> affiliates = getAffiliateUseCase.getAll();
        List<AffiliateResponse> response = affiliates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Get affiliate by ID
     * Accessible by: ADMIN, ANALISTA
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<AffiliateResponse> findById(@PathVariable Long id) {
        Affiliate affiliate = getAffiliateUseCase.findById(id);
        return ResponseEntity.ok(toResponse(affiliate));
    }

    /**
     * Get affiliate by document
     * Accessible by: ADMIN, ANALISTA, AFILIADO (own document only)
     */
    @GetMapping("/document/{document}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA', 'AFILIADO')")
    public ResponseEntity<AffiliateResponse> findByDocument(@PathVariable String document) {
        Affiliate affiliate = getAffiliateUseCase.findByDocument(document);
        return ResponseEntity.ok(toResponse(affiliate));
    }

    /**
     * Update affiliate basic information
     * Accessible by: ADMIN only
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AffiliateResponse> update(@PathVariable Long id, @Valid @RequestBody AffiliateDTO dto) {
        Affiliate affiliate = new Affiliate();
        affiliate.setName(dto.getName());
        affiliate.setSalary(dto.getSalary());

        Affiliate updated = updateAffiliateUseCase.update(id, affiliate);
        return ResponseEntity.ok(toResponse(updated));
    }

    /**
     * Activate an affiliate
     * Accessible by: ADMIN only
     */
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AffiliateResponse> activate(@PathVariable Long id) {
        Affiliate affiliate = updateAffiliateUseCase.activate(id);
        return ResponseEntity.ok(toResponse(affiliate));
    }

    /**
     * Deactivate an affiliate
     * Accessible by: ADMIN only
     */
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AffiliateResponse> deactivate(@PathVariable Long id) {
        Affiliate affiliate = updateAffiliateUseCase.deactivate(id);
        return ResponseEntity.ok(toResponse(affiliate));
    }

    private AffiliateResponse toResponse(Affiliate affiliate) {
        AffiliateResponse response = new AffiliateResponse();
        response.setId(affiliate.getId());
        response.setDocument(affiliate.getDocument());
        response.setName(affiliate.getName());
        response.setSalary(affiliate.getSalary());
        response.setAffiliationDate(affiliate.getAffiliationDate());
        response.setStatus(affiliate.getStatus() != null ? affiliate.getStatus().name() : "ACTIVE");
        response.setMonthsSinceAffiliation(affiliate.getMonthsSinceAffiliation());
        response.setMaxLoanAmount(affiliate.getMaxLoanAmount());
        response.setCanRequestCredit(affiliate.canRequestCredit());
        return response;
    }
}
