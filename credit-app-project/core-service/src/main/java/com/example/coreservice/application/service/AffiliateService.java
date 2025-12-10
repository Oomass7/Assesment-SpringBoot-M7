package com.example.coreservice.application.service;

import com.example.coreservice.application.port.in.GetAffiliateUseCase;
import com.example.coreservice.application.port.in.RegisterAffiliateUseCase;
import com.example.coreservice.application.port.in.UpdateAffiliateUseCase;
import com.example.coreservice.application.port.out.AffiliateRepositoryPort;
import com.example.coreservice.domain.exception.AffiliateNotFoundException;
import com.example.coreservice.domain.model.Affiliate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for affiliate management
 */
@Service
public class AffiliateService implements RegisterAffiliateUseCase, GetAffiliateUseCase, UpdateAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public AffiliateService(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Override
    public Affiliate register(Affiliate affiliate) {
        // Validate affiliate data
        if (!affiliate.isValid()) {
            throw new IllegalArgumentException(
                    "Invalid affiliate data: document, name and salary are required, salary must be > 0");
        }

        // Check if document already exists
        if (affiliateRepository.existsByDocument(affiliate.getDocument())) {
            throw new IllegalArgumentException("Document already registered: " + affiliate.getDocument());
        }

        // Save and return
        return affiliateRepository.save(affiliate);
    }

    @Override
    public Affiliate findById(Long id) {
        return affiliateRepository.findById(id)
                .orElseThrow(() -> new AffiliateNotFoundException("Affiliate not found with ID: " + id));
    }

    @Override
    public Affiliate findByDocument(String document) {
        return affiliateRepository.findByDocument(document)
                .orElseThrow(() -> new AffiliateNotFoundException("Affiliate not found with document: " + document));
    }

    @Override
    public List<Affiliate> getAll() {
        return affiliateRepository.findAll();
    }

    @Override
    public Affiliate update(Long id, Affiliate updatedAffiliate) {
        Affiliate existing = findById(id);

        // Update basic information (document cannot be changed)
        if (updatedAffiliate.getName() != null && !updatedAffiliate.getName().isEmpty()) {
            existing.setName(updatedAffiliate.getName());
        }
        if (updatedAffiliate.getSalary() != null && updatedAffiliate.getSalary() > 0) {
            existing.setSalary(updatedAffiliate.getSalary());
        }

        return affiliateRepository.save(existing);
    }

    @Override
    public Affiliate activate(Long id) {
        Affiliate affiliate = findById(id);
        affiliate.activate();
        return affiliateRepository.save(affiliate);
    }

    @Override
    public Affiliate deactivate(Long id) {
        Affiliate affiliate = findById(id);
        affiliate.deactivate();
        return affiliateRepository.save(affiliate);
    }
}
