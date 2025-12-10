package com.example.authservice.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Role enum
 */
@DisplayName("Role Enum Tests")
class RoleTest {

    @Test
    @DisplayName("Should have AFILIADO role")
    void shouldHaveAfiliadoRole() {
        assertEquals("AFILIADO", Role.AFILIADO.name());
    }

    @Test
    @DisplayName("Should have ANALISTA role")
    void shouldHaveAnalistaRole() {
        assertEquals("ANALISTA", Role.ANALISTA.name());
    }

    @Test
    @DisplayName("Should have ADMIN role")
    void shouldHaveAdminRole() {
        assertEquals("ADMIN", Role.ADMIN.name());
    }

    @Test
    @DisplayName("Should validate AFILIADO as valid")
    void shouldValidateAfiliadoAsValid() {
        assertTrue(Role.isValid("AFILIADO"));
    }

    @Test
    @DisplayName("Should validate ANALISTA as valid")
    void shouldValidateAnalistaAsValid() {
        assertTrue(Role.isValid("ANALISTA"));
    }

    @Test
    @DisplayName("Should validate ADMIN as valid")
    void shouldValidateAdminAsValid() {
        assertTrue(Role.isValid("ADMIN"));
    }

    @Test
    @DisplayName("Should validate lowercase roles as valid")
    void shouldValidateLowercaseRolesAsValid() {
        assertTrue(Role.isValid("afiliado"));
        assertTrue(Role.isValid("analista"));
        assertTrue(Role.isValid("admin"));
    }

    @Test
    @DisplayName("Should invalidate unknown roles")
    void shouldInvalidateUnknownRoles() {
        assertFalse(Role.isValid("UNKNOWN"));
        assertFalse(Role.isValid("USER"));
        assertFalse(Role.isValid(""));
    }

    @Test
    @DisplayName("Should invalidate null role")
    void shouldInvalidateNullRole() {
        assertFalse(Role.isValid(null));
    }

    @Test
    @DisplayName("Should have exactly 3 roles")
    void shouldHaveExactly3Roles() {
        assertEquals(3, Role.values().length);
    }
}
