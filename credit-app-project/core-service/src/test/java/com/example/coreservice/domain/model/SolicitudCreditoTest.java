package com.example.coreservice.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests del modelo SolicitudCredito")
class SolicitudCreditoTest {

    @Test
    @DisplayName("Debe crear una solicitud válida")
    void debeCrearSolicitudValida() {
        SolicitudCredito solicitud = new SolicitudCredito("12345678", "Juan Pérez", 50000.0, 24);
        
        assertNotNull(solicitud);
        assertEquals("Juan Pérez", solicitud.getNombreCliente());
        assertEquals("PENDIENTE", solicitud.getEstado());
    }

    @Test
    @DisplayName("Debe aprobar solicitud")
    void debeAprobarSolicitud() {
        SolicitudCredito solicitud = new SolicitudCredito("87654321", "María García", 30000.0, 12);
        solicitud.aprobar();
        
        assertEquals("APROBADA", solicitud.getEstado());
    }
}
