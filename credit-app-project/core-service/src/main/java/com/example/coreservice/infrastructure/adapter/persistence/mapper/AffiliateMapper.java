package com.example.coreservice.infrastructure.adapter.persistence.mapper;

import com.example.coreservice.domain.model.Affiliate;
import com.example.coreservice.infrastructure.adapter.persistence.entity.AffiliateEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between Affiliate domain model and AffiliateEntity
 */
@Component
public class AffiliateMapper {

    public AffiliateEntity toEntity(Affiliate affiliate) {
        if (affiliate == null)
            return null;

        AffiliateEntity entity = new AffiliateEntity();
        entity.setId(affiliate.getId());
        entity.setDocument(affiliate.getDocument());
        entity.setName(affiliate.getName());
        entity.setSalary(affiliate.getSalary());
        entity.setAffiliationDate(affiliate.getAffiliationDate());
        entity.setStatus(toEntityStatus(affiliate.getStatus()));
        return entity;
    }

    public Affiliate toDomain(AffiliateEntity entity) {
        if (entity == null)
            return null;

        Affiliate affiliate = new Affiliate();
        affiliate.setId(entity.getId());
        affiliate.setDocument(entity.getDocument());
        affiliate.setName(entity.getName());
        affiliate.setSalary(entity.getSalary());
        affiliate.setAffiliationDate(entity.getAffiliationDate());
        affiliate.setStatus(toDomainStatus(entity.getStatus()));
        return affiliate;
    }

    private AffiliateEntity.AffiliateStatusEntity toEntityStatus(Affiliate.AffiliateStatus status) {
        if (status == null)
            return AffiliateEntity.AffiliateStatusEntity.ACTIVE;
        return switch (status) {
            case ACTIVE -> AffiliateEntity.AffiliateStatusEntity.ACTIVE;
            case INACTIVE -> AffiliateEntity.AffiliateStatusEntity.INACTIVE;
        };
    }

    private Affiliate.AffiliateStatus toDomainStatus(AffiliateEntity.AffiliateStatusEntity status) {
        if (status == null)
            return Affiliate.AffiliateStatus.ACTIVE;
        return switch (status) {
            case ACTIVE -> Affiliate.AffiliateStatus.ACTIVE;
            case INACTIVE -> Affiliate.AffiliateStatus.INACTIVE;
        };
    }
}
