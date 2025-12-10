package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.Affiliate;

/**
 * Input port - Use case for updating affiliates
 */
public interface UpdateAffiliateUseCase {
    Affiliate update(Long id, Affiliate affiliate);

    Affiliate activate(Long id);

    Affiliate deactivate(Long id);
}
