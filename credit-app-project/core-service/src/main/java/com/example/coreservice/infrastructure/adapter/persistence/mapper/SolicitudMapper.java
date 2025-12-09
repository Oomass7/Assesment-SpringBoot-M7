package com.example.coreservice.infrastructure.adapter.persistence.mapper;

import com.example.coreservice.domain.model.SolicitudCredito;
import com.example.coreservice.infrastructure.adapter.persistence.entity.SolicitudEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entidad de dominio y entidad JPA
 */
@Component
public class SolicitudMapper {

    public SolicitudCredito toDomain(SolicitudEntity entity) {
        if (entity == null) {
            return null;
        }
        
        SolicitudCredito solicitud = new SolicitudCredito();
        solicitud.setId(entity.getId());
        solicitud.setDocumentoCliente(entity.getDocumentoCliente());
        solicitud.setNombreCliente(entity.getNombreCliente());
        solicitud.setMontoSolicitado(entity.getMontoSolicitado());
        solicitud.setPlazoMeses(entity.getPlazoMeses());
        solicitud.setEstado(entity.getEstado());
        solicitud.setScoreRiesgo(entity.getScoreRiesgo());
        solicitud.setNivelRiesgo(entity.getNivelRiesgo());
        solicitud.setFechaSolicitud(entity.getFechaSolicitud());
        solicitud.setFechaEvaluacion(entity.getFechaEvaluacion());
        
        return solicitud;
    }

    public SolicitudEntity toEntity(SolicitudCredito solicitud) {
        if (solicitud == null) {
            return null;
        }
        
        SolicitudEntity entity = new SolicitudEntity();
        entity.setId(solicitud.getId());
        entity.setDocumentoCliente(solicitud.getDocumentoCliente());
        entity.setNombreCliente(solicitud.getNombreCliente());
        entity.setMontoSolicitado(solicitud.getMontoSolicitado());
        entity.setPlazoMeses(solicitud.getPlazoMeses());
        entity.setEstado(solicitud.getEstado());
        entity.setScoreRiesgo(solicitud.getScoreRiesgo());
        entity.setNivelRiesgo(solicitud.getNivelRiesgo());
        entity.setFechaSolicitud(solicitud.getFechaSolicitud());
        entity.setFechaEvaluacion(solicitud.getFechaEvaluacion());
        
        return entity;
    }
}
