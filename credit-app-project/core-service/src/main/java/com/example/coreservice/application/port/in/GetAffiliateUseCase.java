package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.Affiliate;
import java.util.List;

/**
 * Input port - Use case for querying affiliates
 */
public interface GetAffiliateUseCase {
    Affiliate findById(Long id);

    Affiliate findByDocument(String document);

    List<Affiliate> getAll();
}
