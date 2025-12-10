package com.example.coreservice.application.service;

import com.example.coreservice.application.port.out.RequestRepositoryPort;
import com.example.coreservice.application.port.out.RiskServicePort;
import com.example.coreservice.domain.exception.RequestNotFoundException;
import com.example.coreservice.domain.model.CreditRequest;
import com.example.coreservice.domain.model.RiskEvaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RequestService using Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RequestService Tests")
class RequestServiceTest {

    @Mock
    private RequestRepositoryPort requestRepository;

    @Mock
    private RiskServicePort riskService;

    @InjectMocks
    private RequestService requestService;

    private CreditRequest creditRequest;
    private RiskEvaluation riskEvaluation;

    @BeforeEach
    void setUp() {
        creditRequest = new CreditRequest("12345678A", "John Doe", 10000.0, 12);
        creditRequest.setId(1L);

        riskEvaluation = new RiskEvaluation("12345678A", 250, "LOW");
    }

    @Nested
    @DisplayName("Create Request Tests")
    class CreateRequestTests {

        @Test
        @DisplayName("Should create and auto-approve low risk request")
        void shouldCreateAndAutoApproveLowRiskRequest() {
            when(riskService.evaluate(anyString(), anyDouble(), anyInt())).thenReturn(riskEvaluation);
            when(requestRepository.save(any(CreditRequest.class))).thenAnswer(i -> {
                CreditRequest req = i.getArgument(0);
                req.setId(1L);
                return req;
            });

            CreditRequest result = requestService.create(creditRequest);

            assertNotNull(result);
            assertEquals("APPROVED", result.getStatus());
            assertEquals(250, result.getRiskScore());
            assertEquals("LOW", result.getRiskLevel());
            verify(requestRepository, times(2)).save(any()); // Initial + after risk
        }

        @Test
        @DisplayName("Should create and auto-reject high risk request")
        void shouldCreateAndAutoRejectHighRiskRequest() {
            RiskEvaluation highRisk = new RiskEvaluation("12345678A", 850, "HIGH");

            when(riskService.evaluate(anyString(), anyDouble(), anyInt())).thenReturn(highRisk);
            when(requestRepository.save(any(CreditRequest.class))).thenAnswer(i -> {
                CreditRequest req = i.getArgument(0);
                req.setId(1L);
                return req;
            });

            CreditRequest result = requestService.create(creditRequest);

            assertEquals("REJECTED", result.getStatus());
            assertEquals(850, result.getRiskScore());
        }

        @Test
        @DisplayName("Should create request pending for medium risk")
        void shouldCreateRequestPendingForMediumRisk() {
            RiskEvaluation mediumRisk = new RiskEvaluation("12345678A", 500, "MEDIUM");

            when(riskService.evaluate(anyString(), anyDouble(), anyInt())).thenReturn(mediumRisk);
            when(requestRepository.save(any(CreditRequest.class))).thenAnswer(i -> {
                CreditRequest req = i.getArgument(0);
                req.setId(1L);
                return req;
            });

            CreditRequest result = requestService.create(creditRequest);

            assertEquals("PENDING", result.getStatus());
            assertEquals(500, result.getRiskScore());
        }

        @Test
        @DisplayName("Should throw exception for invalid request")
        void shouldThrowExceptionForInvalidRequest() {
            CreditRequest invalidRequest = new CreditRequest("", "", 0.0, 0);

            assertThrows(IllegalArgumentException.class,
                    () -> requestService.create(invalidRequest));

            verify(requestRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Find Request Tests")
    class FindRequestTests {

        @Test
        @DisplayName("Should find request by ID")
        void shouldFindRequestById() {
            when(requestRepository.findById(1L)).thenReturn(Optional.of(creditRequest));

            CreditRequest result = requestService.findById(1L);

            assertNotNull(result);
            assertEquals(1L, result.getId());
        }

        @Test
        @DisplayName("Should throw exception when request not found")
        void shouldThrowExceptionWhenNotFound() {
            when(requestRepository.findById(999L)).thenReturn(Optional.empty());

            assertThrows(RequestNotFoundException.class,
                    () -> requestService.findById(999L));
        }

        @Test
        @DisplayName("Should find requests by document")
        void shouldFindRequestsByDocument() {
            when(requestRepository.findByDocument("12345678A"))
                    .thenReturn(Arrays.asList(creditRequest));

            List<CreditRequest> result = requestService.findByDocument("12345678A");

            assertEquals(1, result.size());
            assertEquals("12345678A", result.get(0).getClientDocument());
        }

        @Test
        @DisplayName("Should get all requests")
        void shouldGetAllRequests() {
            CreditRequest request2 = new CreditRequest("23456789B", "Jane Doe", 5000.0, 6);
            when(requestRepository.buscarTodas()).thenReturn(Arrays.asList(creditRequest, request2));

            List<CreditRequest> result = requestService.getAll();

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("Should find requests by status")
        void shouldFindRequestsByStatus() {
            when(requestRepository.findByStatus("PENDING")).thenReturn(Arrays.asList(creditRequest));

            List<CreditRequest> result = requestService.findByStatus("PENDING");

            assertEquals(1, result.size());
            verify(requestRepository).findByStatus("PENDING");
        }

        @Test
        @DisplayName("Should find requests by user email")
        void shouldFindRequestsByUserEmail() {
            when(requestRepository.findByDocument("user@email.com"))
                    .thenReturn(Arrays.asList(creditRequest));

            List<CreditRequest> result = requestService.findByUserEmail("user@email.com");

            assertEquals(1, result.size());
        }
    }
}
