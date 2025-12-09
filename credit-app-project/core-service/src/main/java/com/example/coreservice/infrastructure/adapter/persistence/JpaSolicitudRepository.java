package com.example.coreservice.infrastructure.adapter.persistence;

import com.example.coreservice.infrastructure.adapter.persistence.entity.SolicitudEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA Spring Data
 */
@Repository
public interface JpaSolicitudRepository extends JpaRepository<SolicitudEntity, Long> {
    List<SolicitudEntity> findByDocumentoCliente(String documentoCliente);
}
