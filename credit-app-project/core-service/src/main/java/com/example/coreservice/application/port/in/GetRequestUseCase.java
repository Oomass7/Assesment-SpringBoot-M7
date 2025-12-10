package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.CreditRequest;
import java.util.List;

/**
 * Input port - Use case for querying requests
 */
public interface GetRequestUseCase {
    CreditRequest findById(Long id);

    List<CreditRequest> getAll();

    List<CreditRequest> findByDocument(String document);

    List<CreditRequest> findByStatus(String status);

    List<CreditRequest> findByUserEmail(String userEmail);
}
