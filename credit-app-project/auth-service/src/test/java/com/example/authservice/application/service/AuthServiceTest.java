package com.example.authservice.application.service;

import com.example.authservice.application.port.out.EncryptionPort;
import com.example.authservice.application.port.out.TokenGeneratorPort;
import com.example.authservice.application.port.out.UserRepositoryPort;
import com.example.authservice.domain.exception.InvalidCredentialsException;
import com.example.authservice.domain.exception.UserNotFoundException;
import com.example.authservice.domain.model.TokenAuth;
import com.example.authservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService using Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private EncryptionPort encryption;

    @Mock
    private TokenGeneratorPort tokenGenerator;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("test@email.com", "Password123", "John Doe", "AFILIADO");
        user.setId(1L);
    }

    @Nested
    @DisplayName("Register User Tests")
    class RegisterUserTests {

        @Test
        @DisplayName("Should register user successfully")
        void shouldRegisterUserSuccessfully() {
            when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
            when(encryption.encrypt("Password123")).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenReturn(user);

            User result = authService.register(user);

            assertNotNull(result);
            assertEquals("test@email.com", result.getEmail());
            verify(encryption).encrypt("Password123");
            verify(userRepository).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw exception for existing email")
        void shouldThrowExceptionForExistingEmail() {
            when(userRepository.existsByEmail("test@email.com")).thenReturn(true);

            assertThrows(IllegalArgumentException.class,
                    () -> authService.register(user));

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw exception for invalid role")
        void shouldThrowExceptionForInvalidRole() {
            user.setRole("INVALID_ROLE");
            when(userRepository.existsByEmail("test@email.com")).thenReturn(false);

            assertThrows(IllegalArgumentException.class,
                    () -> authService.register(user));

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Should throw exception for invalid user data")
        void shouldThrowExceptionForInvalidUserData() {
            User invalidUser = new User("", "", "", "AFILIADO");

            assertThrows(IllegalArgumentException.class,
                    () -> authService.register(invalidUser));

            verify(userRepository, never()).save(any(User.class));
        }

        @Test
        @DisplayName("Should normalize role to uppercase")
        void shouldNormalizeRoleToUppercase() {
            User lowerCaseRoleUser = new User("test2@email.com", "Password123", "John Doe", "afiliado");

            when(userRepository.existsByEmail("test2@email.com")).thenReturn(false);
            when(encryption.encrypt(anyString())).thenReturn("encodedPassword");
            when(userRepository.save(any(User.class))).thenAnswer(i -> {
                User u = i.getArgument(0);
                assertEquals("AFILIADO", u.getRole());
                return u;
            });

            authService.register(lowerCaseRoleUser);

            verify(userRepository).save(any(User.class));
        }
    }

    @Nested
    @DisplayName("Authenticate User Tests")
    class AuthenticateUserTests {

        @Test
        @DisplayName("Should authenticate user successfully")
        void shouldAuthenticateUserSuccessfully() {
            TokenAuth expectedToken = new TokenAuth("jwt.token.here", "Bearer",
                    java.time.LocalDateTime.now().plusHours(1));
            user.setPassword("encodedPassword");

            when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(encryption.verify("Password123", "encodedPassword")).thenReturn(true);
            when(tokenGenerator.generate(any(User.class))).thenReturn(expectedToken);

            TokenAuth result = authService.authenticate("test@email.com", "Password123");

            assertNotNull(result);
            assertEquals("jwt.token.here", result.getToken());
            assertEquals("Bearer", result.getType());
        }

        @Test
        @DisplayName("Should throw exception for non-existent user")
        void shouldThrowExceptionForNonExistentUser() {
            when(userRepository.findByEmail("unknown@email.com")).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class,
                    () -> authService.authenticate("unknown@email.com", "password"));
        }

        @Test
        @DisplayName("Should throw exception for wrong password")
        void shouldThrowExceptionForWrongPassword() {
            user.setPassword("encodedPassword");

            when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(encryption.verify("wrongPassword", "encodedPassword")).thenReturn(false);

            assertThrows(InvalidCredentialsException.class,
                    () -> authService.authenticate("test@email.com", "wrongPassword"));
        }

        @Test
        @DisplayName("Should throw exception for inactive user")
        void shouldThrowExceptionForInactiveUser() {
            user.setPassword("encodedPassword");
            user.deactivate();

            when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));
            when(encryption.verify("Password123", "encodedPassword")).thenReturn(true);

            assertThrows(InvalidCredentialsException.class,
                    () -> authService.authenticate("test@email.com", "Password123"));
        }
    }
}
