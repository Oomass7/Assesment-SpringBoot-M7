package com.example.coreservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CreditRequest domain entity
 */
@DisplayName("CreditRequest Domain Model Tests")
class CreditRequestTest {

    private CreditRequest creditRequest;

    @BeforeEach
    void setUp() {
        creditRequest = new CreditRequest("12345678A", "John Doe", 10000.0, 12);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create credit request with correct values")
        void shouldCreateCreditRequestWithCorrectValues() {
            assertEquals("12345678A", creditRequest.getClientDocument());
            assertEquals("John Doe", creditRequest.getClientName());
            assertEquals(10000.0, creditRequest.getRequestedAmount());
            assertEquals(12, creditRequest.getTermMonths());
            assertEquals("PENDING", creditRequest.getStatus());
            assertNotNull(creditRequest.getRequestDate());
        }

        @Test
        @DisplayName("Should create credit request with proposed rate")
        void shouldCreateCreditRequestWithProposedRate() {
            CreditRequest request = new CreditRequest("12345678A", "John Doe", 10000.0, 12, 0.15);
            assertEquals(0.15, request.getProposedRate());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should be valid with all required fields")
        void shouldBeValidWithAllFields() {
            assertTrue(creditRequest.isValid());
        }

        @Test
        @DisplayName("Should be invalid without client document")
        void shouldBeInvalidWithoutClientDocument() {
            creditRequest.setClientDocument(null);
            assertFalse(creditRequest.isValid());
        }

        @Test
        @DisplayName("Should be invalid with empty client document")
        void shouldBeInvalidWithEmptyClientDocument() {
            creditRequest.setClientDocument("");
            assertFalse(creditRequest.isValid());
        }

        @Test
        @DisplayName("Should be invalid without amount")
        void shouldBeInvalidWithoutAmount() {
            creditRequest.setRequestedAmount(null);
            assertFalse(creditRequest.isValid());
        }

        @Test
        @DisplayName("Should be invalid with negative amount")
        void shouldBeInvalidWithNegativeAmount() {
            creditRequest.setRequestedAmount(-1000.0);
            assertFalse(creditRequest.isValid());
        }

        @Test
        @DisplayName("Should be invalid with zero term")
        void shouldBeInvalidWithZeroTerm() {
            creditRequest.setTermMonths(0);
            assertFalse(creditRequest.isValid());
        }
    }

    @Nested
    @DisplayName("Status Change Tests")
    class StatusChangeTests {

        @Test
        @DisplayName("Should approve credit request")
        void shouldApproveCreditRequest() {
            creditRequest.approve();

            assertEquals("APPROVED", creditRequest.getStatus());
            assertNotNull(creditRequest.getEvaluationDate());
        }

        @Test
        @DisplayName("Should reject credit request with reason")
        void shouldRejectCreditRequestWithReason() {
            creditRequest.reject("High risk level");

            assertEquals("REJECTED", creditRequest.getStatus());
            assertEquals("High risk level", creditRequest.getRejectionReason());
            assertNotNull(creditRequest.getEvaluationDate());
        }

        @Test
        @DisplayName("Should reject credit request with default reason")
        void shouldRejectCreditRequestWithDefaultReason() {
            creditRequest.reject();

            assertEquals("REJECTED", creditRequest.getStatus());
            assertEquals("Risk level too high", creditRequest.getRejectionReason());
        }
    }

    @Nested
    @DisplayName("Risk Evaluation Tests")
    class RiskEvaluationTests {

        @Test
        @DisplayName("Should auto-approve on LOW risk")
        void shouldAutoApproveOnLowRisk() {
            creditRequest.assignRiskEvaluation(250, "LOW");

            assertEquals(250, creditRequest.getRiskScore());
            assertEquals("LOW", creditRequest.getRiskLevel());
            assertEquals("APPROVED", creditRequest.getStatus());
        }

        @Test
        @DisplayName("Should auto-reject on HIGH risk")
        void shouldAutoRejectOnHighRisk() {
            creditRequest.assignRiskEvaluation(850, "HIGH");

            assertEquals(850, creditRequest.getRiskScore());
            assertEquals("HIGH", creditRequest.getRiskLevel());
            assertEquals("REJECTED", creditRequest.getStatus());
        }

        @Test
        @DisplayName("Should remain pending on MEDIUM risk")
        void shouldRemainPendingOnMediumRisk() {
            creditRequest.assignRiskEvaluation(500, "MEDIUM");

            assertEquals(500, creditRequest.getRiskScore());
            assertEquals("MEDIUM", creditRequest.getRiskLevel());
            assertEquals("PENDING", creditRequest.getStatus());
        }
    }

    @Nested
    @DisplayName("Payment Calculation Tests")
    class PaymentCalculationTests {

        @Test
        @DisplayName("Should calculate monthly payment with proposed rate")
        void shouldCalculateMonthlyPaymentWithProposedRate() {
            creditRequest.setProposedRate(0.12);

            Double payment = creditRequest.calculateMonthlyPayment();

            assertNotNull(payment);
            assertTrue(payment > 0);
            assertTrue(payment > creditRequest.getRequestedAmount() / creditRequest.getTermMonths());
        }

        @Test
        @DisplayName("Should calculate monthly payment with default rate")
        void shouldCalculateMonthlyPaymentWithDefaultRate() {
            creditRequest.setProposedRate(null);

            Double payment = creditRequest.calculateMonthlyPayment();

            assertNotNull(payment);
            assertTrue(payment > 0);
        }

        @Test
        @DisplayName("Should return zero for invalid data")
        void shouldReturnZeroForInvalidData() {
            creditRequest.setTermMonths(0);

            Double payment = creditRequest.calculateMonthlyPayment();

            assertEquals(0.0, payment);
        }
    }
}
