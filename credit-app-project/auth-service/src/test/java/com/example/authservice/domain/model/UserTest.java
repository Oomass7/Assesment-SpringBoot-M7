package com.example.authservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for User domain entity
 */
@DisplayName("User Domain Model Tests")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test@email.com", "Password123", "John Doe", "AFILIADO");
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create user with correct values")
        void shouldCreateUserWithCorrectValues() {
            assertEquals("test@email.com", user.getEmail());
            assertEquals("Password123", user.getPassword());
            assertEquals("John Doe", user.getName());
            assertEquals("AFILIADO", user.getRole());
            assertTrue(user.isActive());
            assertNotNull(user.getCreatedAt());
        }

        @Test
        @DisplayName("Should create user with default constructor")
        void shouldCreateUserWithDefaultConstructor() {
            User emptyUser = new User();
            assertNull(emptyUser.getEmail());
            assertNull(emptyUser.getPassword());
            assertTrue(emptyUser.isActive()); // Active by default
            assertNotNull(emptyUser.getCreatedAt());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should be valid with all required fields")
        void shouldBeValidWithAllFields() {
            assertTrue(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid without email")
        void shouldBeInvalidWithoutEmail() {
            user.setEmail(null);
            assertFalse(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid with empty email")
        void shouldBeInvalidWithEmptyEmail() {
            user.setEmail("");
            assertFalse(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid without password")
        void shouldBeInvalidWithoutPassword() {
            user.setPassword(null);
            assertFalse(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid with short password")
        void shouldBeInvalidWithShortPassword() {
            user.setPassword("12345"); // less than 6 characters
            assertFalse(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid without name")
        void shouldBeInvalidWithoutName() {
            user.setName(null);
            assertFalse(user.isValid());
        }

        @Test
        @DisplayName("Should be invalid with empty name")
        void shouldBeInvalidWithEmptyName() {
            user.setName("");
            assertFalse(user.isValid());
        }
    }

    @Nested
    @DisplayName("Status Tests")
    class StatusTests {

        @Test
        @DisplayName("Should activate user")
        void shouldActivateUser() {
            user.deactivate();
            assertFalse(user.isActive());

            user.activate();
            assertTrue(user.isActive());
        }

        @Test
        @DisplayName("Should deactivate user")
        void shouldDeactivateUser() {
            assertTrue(user.isActive());

            user.deactivate();
            assertFalse(user.isActive());
        }
    }

    @Nested
    @DisplayName("Role Tests")
    class RoleTests {

        @Test
        @DisplayName("Should accept AFILIADO role")
        void shouldAcceptAfiliadoRole() {
            user.setRole("AFILIADO");
            assertEquals("AFILIADO", user.getRole());
        }

        @Test
        @DisplayName("Should accept ANALISTA role")
        void shouldAcceptAnalistaRole() {
            user.setRole("ANALISTA");
            assertEquals("ANALISTA", user.getRole());
        }

        @Test
        @DisplayName("Should accept ADMIN role")
        void shouldAcceptAdminRole() {
            user.setRole("ADMIN");
            assertEquals("ADMIN", user.getRole());
        }
    }

    @Nested
    @DisplayName("Getters and Setters Tests")
    class GettersSettersTests {

        @Test
        @DisplayName("Should set and get ID")
        void shouldSetAndGetId() {
            user.setId(123L);
            assertEquals(123L, user.getId());
        }

        @Test
        @DisplayName("Should set and get email")
        void shouldSetAndGetEmail() {
            user.setEmail("new@email.com");
            assertEquals("new@email.com", user.getEmail());
        }

        @Test
        @DisplayName("Should set and get password")
        void shouldSetAndGetPassword() {
            user.setPassword("NewPassword456");
            assertEquals("NewPassword456", user.getPassword());
        }
    }
}
