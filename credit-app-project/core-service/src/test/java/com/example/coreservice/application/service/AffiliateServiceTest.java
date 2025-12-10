package com.example.coreservice.application.service;

import com.example.coreservice.application.port.out.AffiliateRepositoryPort;
import com.example.coreservice.domain.exception.AffiliateNotFoundException;
import com.example.coreservice.domain.model.Affiliate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AffiliateService using Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AffiliateService Tests")
class AffiliateServiceTest {

    @Mock
    private AffiliateRepositoryPort affiliateRepository;

    @InjectMocks
    private AffiliateService affiliateService;

    private Affiliate affiliate;

    @BeforeEach
    void setUp() {
        affiliate = new Affiliate("12345678A", "John Doe", 3000.0);
        affiliate.setId(1L);
        affiliate.setAffiliationDate(LocalDate.now().minusMonths(12));
    }

    @Nested
    @DisplayName("Register Affiliate Tests")
    class RegisterAffiliateTests {

        @Test
        @DisplayName("Should register affiliate successfully")
        void shouldRegisterAffiliateSuccessfully() {
            when(affiliateRepository.existsByDocument("12345678A")).thenReturn(false);
            when(affiliateRepository.save(any(Affiliate.class))).thenReturn(affiliate);

            Affiliate result = affiliateService.register(affiliate);

            assertNotNull(result);
            assertEquals("12345678A", result.getDocument());
            assertEquals("John Doe", result.getName());
            verify(affiliateRepository).save(any(Affiliate.class));
        }

        @Test
        @DisplayName("Should throw exception for duplicate document")
        void shouldThrowExceptionForDuplicateDocument() {
            when(affiliateRepository.existsByDocument("12345678A")).thenReturn(true);

            assertThrows(IllegalArgumentException.class,
                    () -> affiliateService.register(affiliate));

            verify(affiliateRepository, never()).save(any(Affiliate.class));
        }

        @Test
        @DisplayName("Should throw exception for invalid affiliate")
        void shouldThrowExceptionForInvalidAffiliate() {
            Affiliate invalidAffiliate = new Affiliate("", "John", 0.0);

            assertThrows(IllegalArgumentException.class,
                    () -> affiliateService.register(invalidAffiliate));

            verify(affiliateRepository, never()).save(any(Affiliate.class));
        }
    }

    @Nested
    @DisplayName("Find Affiliate Tests")
    class FindAffiliateTests {

        @Test
        @DisplayName("Should find affiliate by ID")
        void shouldFindAffiliateById() {
            when(affiliateRepository.findById(1L)).thenReturn(Optional.of(affiliate));

            Affiliate result = affiliateService.findById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            verify(affiliateRepository).findById(1L);
        }

        @Test
        @DisplayName("Should throw exception when affiliate not found by ID")
        void shouldThrowExceptionWhenNotFoundById() {
            when(affiliateRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(AffiliateNotFoundException.class,
                    () -> affiliateService.findById(999L));
        }

        @Test
        @DisplayName("Should find affiliate by document")
        void shouldFindAffiliateByDocument() {
            when(affiliateRepository.findByDocument("12345678A")).thenReturn(Optional.of(affiliate));

            Affiliate result = affiliateService.findByDocument("12345678A");

            assertNotNull(result);
            assertEquals("12345678A", result.getDocument());
        }

        @Test
        @DisplayName("Should throw exception when affiliate not found by document")
        void shouldThrowExceptionWhenNotFoundByDocument() {
            when(affiliateRepository.findByDocument("INVALID")).thenReturn(Optional.empty());

            assertThrows(AffiliateNotFoundException.class,
                    () -> affiliateService.findByDocument("INVALID"));
        }

        @Test
        @DisplayName("Should get all affiliates")
        void shouldGetAllAffiliates() {
            Affiliate affiliate2 = new Affiliate("23456789B", "Jane Doe", 4000.0);
            when(affiliateRepository.findAll()).thenReturn(Arrays.asList(affiliate, affiliate2));

            List<Affiliate> result = affiliateService.getAll();

            assertEquals(2, result.size());
            verify(affiliateRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Update Affiliate Tests")
    class UpdateAffiliateTests {

        @Test
        @DisplayName("Should update affiliate successfully")
        void shouldUpdateAffiliateSuccessfully() {
            Affiliate updatedData = new Affiliate();
            updatedData.setName("John Updated");
            updatedData.setSalary(4000.0);

            when(affiliateRepository.findById(1L)).thenReturn(Optional.of(affiliate));
            when(affiliateRepository.save(any(Affiliate.class))).thenAnswer(i -> i.getArgument(0));

            Affiliate result = affiliateService.update(1L, updatedData);

            assertEquals("John Updated", result.getName());
            assertEquals(4000.0, result.getSalary());
            verify(affiliateRepository).save(any(Affiliate.class));
        }

        @Test
        @DisplayName("Should activate affiliate")
        void shouldActivateAffiliate() {
            affiliate.deactivate();
            when(affiliateRepository.findById(1L)).thenReturn(Optional.of(affiliate));
            when(affiliateRepository.save(any(Affiliate.class))).thenAnswer(i -> i.getArgument(0));

            Affiliate result = affiliateService.activate(1L);

            assertTrue(result.isActive());
        }

        @Test
        @DisplayName("Should deactivate affiliate")
        void shouldDeactivateAffiliate() {
            when(affiliateRepository.findById(1L)).thenReturn(Optional.of(affiliate));
            when(affiliateRepository.save(any(Affiliate.class))).thenAnswer(i -> i.getArgument(0));

            Affiliate result = affiliateService.deactivate(1L);

            assertFalse(result.isActive());
        }
    }
}
