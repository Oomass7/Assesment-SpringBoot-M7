package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.Affiliate;

/**
 * Input port - Use case for registering affiliates
 */
public interface RegisterAffiliateUseCase {
    Affiliate register(Affiliate affiliate);
}
