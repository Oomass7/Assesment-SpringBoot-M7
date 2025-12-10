package com.example.coreservice.infrastructure.adapter.persistence.mapper;

import com.example.coreservice.domain.model.CreditRequest;
import com.example.coreservice.infrastructure.adapter.persistence.entity.RequestEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entidad de dominio y entidad JPA
 */
@Component
public class RequestMapper {

    public CreditRequest toDomain(RequestEntity entity) {
        if (entity == null) {
            return null;
        }
        
        CreditRequest request = new CreditRequest();
        request.setId(entity.getId());
        request.setClientDocument(entity.getClientDocument());
        request.setClientName(entity.getClientName());
        request.setRequestedAmount(entity.getRequestedAmount());
        request.setTermMonths(entity.getTermMonths());
        request.setStatus(entity.getStatus());
        request.setRiskScore(entity.getRiskScore());
        request.setRiskLevel(entity.getRiskLevel());
        request.setRequestDate(entity.getRequestDate());
        request.setEvaluationDate(entity.getEvaluationDate());
        
        return request;
    }

    public RequestEntity toEntity(CreditRequest request) {
        if (request == null) {
            return null;
        }
        
        RequestEntity entity = new RequestEntity();
        entity.setId(request.getId());
        entity.setClientDocument(request.getClientDocument());
        entity.setClientName(request.getClientName());
        entity.setRequestedAmount(request.getRequestedAmount());
        entity.setTermMonths(request.getTermMonths());
        entity.setStatus(request.getStatus());
        entity.setRiskScore(request.getRiskScore());
        entity.setRiskLevel(request.getRiskLevel());
        entity.setRequestDate(request.getRequestDate());
        entity.setEvaluationDate(request.getEvaluationDate());
        
        return entity;
    }
}
