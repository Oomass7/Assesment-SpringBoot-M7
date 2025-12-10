package com.example.coreservice.infrastructure.adapter.persistence.mapper;

import com.example.coreservice.domain.model.CreditRequest;
import com.example.coreservice.infrastructure.adapter.persistence.entity.RequestEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain entity and JPA entity for credit requests
 */
@Component
public class RequestMapper {

    public CreditRequest toDomain(RequestEntity entity) {
        if (entity == null) {
            return null;
        }

        CreditRequest request = new CreditRequest();
        request.setId(entity.getId());
        request.setAffiliateId(entity.getAffiliateId());
        request.setClientDocument(entity.getClientDocument());
        request.setClientName(entity.getClientName());
        request.setRequestedAmount(entity.getRequestedAmount());
        request.setTermMonths(entity.getTermMonths());
        request.setProposedRate(entity.getProposedRate());
        request.setStatus(entity.getStatus());
        request.setRiskScore(entity.getRiskScore());
        request.setRiskLevel(entity.getRiskLevel());
        request.setRequestDate(entity.getRequestDate());
        request.setEvaluationDate(entity.getEvaluationDate());
        request.setRejectionReason(entity.getRejectionReason());

        return request;
    }

    public RequestEntity toEntity(CreditRequest request) {
        if (request == null) {
            return null;
        }

        RequestEntity entity = new RequestEntity();
        entity.setId(request.getId());
        entity.setAffiliateId(request.getAffiliateId());
        entity.setClientDocument(request.getClientDocument());
        entity.setClientName(request.getClientName());
        entity.setRequestedAmount(request.getRequestedAmount());
        entity.setTermMonths(request.getTermMonths());
        entity.setProposedRate(request.getProposedRate());
        entity.setStatus(request.getStatus());
        entity.setRiskScore(request.getRiskScore());
        entity.setRiskLevel(request.getRiskLevel());
        entity.setRequestDate(request.getRequestDate());
        entity.setEvaluationDate(request.getEvaluationDate());
        entity.setRejectionReason(request.getRejectionReason());

        return entity;
    }
}
