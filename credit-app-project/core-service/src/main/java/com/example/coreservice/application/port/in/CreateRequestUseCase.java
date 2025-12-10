package com.example.coreservice.application.port.in;

import com.example.coreservice.domain.model.CreditRequest;

/**
 * Puerto de entrada - Caso de uso para crear requestes
 */
public interface CreateRequestUseCase {
    CreditRequest create(CreditRequest request);
}
