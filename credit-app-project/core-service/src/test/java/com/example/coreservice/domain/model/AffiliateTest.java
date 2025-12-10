package com.example.coreservice.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Affiliate domain entity
 */
@DisplayName("Affiliate Domain Model Tests")
class AffiliateTest {

    private Affiliate affiliate;

    @BeforeEach
    void setUp() {
        affiliate = new Affiliate("12345678A", "John Doe", 3000.0);
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create affiliate with correct values")
        void shouldCreateAffiliateWithCorrectValues() {
            assertEquals("12345678A", affiliate.getDocument());
            assertEquals("John Doe", affiliate.getName());
            assertEquals(3000.0, affiliate.getSalary());
            assertEquals(LocalDate.now(), affiliate.getAffiliationDate());
            assertEquals(Affiliate.AffiliateStatus.ACTIVE, affiliate.getStatus());
        }

        @Test
        @DisplayName("Should create affiliate with default constructor")
        void shouldCreateAffiliateWithDefaultConstructor() {
            Affiliate emptyAffiliate = new Affiliate();
            assertNull(emptyAffiliate.getDocument());
            assertNull(emptyAffiliate.getName());
            assertNull(emptyAffiliate.getSalary());
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should be valid with all required fields")
        void shouldBeValidWithAllFields() {
            assertTrue(affiliate.isValid());
        }

        @Test
        @DisplayName("Should be invalid without document")
        void shouldBeInvalidWithoutDocument() {
            affiliate.setDocument(null);
            assertFalse(affiliate.isValid());
        }

        @Test
        @DisplayName("Should be invalid with empty document")
        void shouldBeInvalidWithEmptyDocument() {
            affiliate.setDocument("");
            assertFalse(affiliate.isValid());
        }

        @Test
        @DisplayName("Should be invalid without name")
        void shouldBeInvalidWithoutName() {
            affiliate.setName(null);
            assertFalse(affiliate.isValid());
        }

        @Test
        @DisplayName("Should be invalid with negative salary")
        void shouldBeInvalidWithNegativeSalary() {
            affiliate.setSalary(-1000.0);
            assertFalse(affiliate.isValid());
        }

        @Test
        @DisplayName("Should be invalid with zero salary")
        void shouldBeInvalidWithZeroSalary() {
            affiliate.setSalary(0.0);
            assertFalse(affiliate.isValid());
        }
    }

    @Nested
    @DisplayName("Status Tests")
    class StatusTests {

        @Test
        @DisplayName("Should be active by default")
        void shouldBeActiveByDefault() {
            assertTrue(affiliate.isActive());
        }

        @Test
        @DisplayName("Should deactivate affiliate")
        void shouldDeactivateAffiliate() {
            affiliate.deactivate();
            assertFalse(affiliate.isActive());
            assertEquals(Affiliate.AffiliateStatus.INACTIVE, affiliate.getStatus());
        }

        @Test
        @DisplayName("Should activate affiliate")
        void shouldActivateAffiliate() {
            affiliate.deactivate();
            affiliate.activate();
            assertTrue(affiliate.isActive());
            assertEquals(Affiliate.AffiliateStatus.ACTIVE, affiliate.getStatus());
        }
    }

    @Nested
    @DisplayName("Seniority Tests")
    class SeniorityTests {

        @Test
        @DisplayName("Should calculate months since affiliation correctly")
        void shouldCalculateMonthsSinceAffiliation() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(12));
            assertEquals(12, affiliate.getMonthsSinceAffiliation());
        }

        @Test
        @DisplayName("Should have minimum seniority after 6 months")
        void shouldHaveMinimumSeniorityAfter6Months() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(7));
            assertTrue(affiliate.hasMinimumSeniority());
        }

        @Test
        @DisplayName("Should not have minimum seniority before 6 months")
        void shouldNotHaveMinimumSeniorityBefore6Months() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(3));
            assertFalse(affiliate.hasMinimumSeniority());
        }
    }

    @Nested
    @DisplayName("Credit Eligibility Tests")
    class CreditEligibilityTests {

        @Test
        @DisplayName("Should be eligible for credit when active and has seniority")
        void shouldBeEligibleWhenActiveAndHasSeniority() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(12));
            assertTrue(affiliate.canRequestCredit());
        }

        @Test
        @DisplayName("Should not be eligible when inactive")
        void shouldNotBeEligibleWhenInactive() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(12));
            affiliate.deactivate();
            assertFalse(affiliate.canRequestCredit());
        }

        @Test
        @DisplayName("Should not be eligible without minimum seniority")
        void shouldNotBeEligibleWithoutMinimumSeniority() {
            affiliate.setAffiliationDate(LocalDate.now().minusMonths(3));
            assertFalse(affiliate.canRequestCredit());
        }
    }

    @Nested
    @DisplayName("Loan Calculation Tests")
    class LoanCalculationTests {

        @Test
        @DisplayName("Should calculate max loan amount as 10x salary")
        void shouldCalculateMaxLoanAmount() {
            affiliate.setSalary(5000.0);
            assertEquals(50000.0, affiliate.getMaxLoanAmount()); // 10x salary
        }

        @Test
        @DisplayName("Should calculate monthly payment correctly")
        void shouldCalculateMonthlyPayment() {
            double amount = 10000.0;
            int months = 12;
            double rate = 0.12; // 12% annual

            double payment = affiliate.calculateMonthlyPayment(amount, months, rate);

            assertTrue(payment > 0);
            assertTrue(payment > amount / months); // Payment should be higher due to interest
        }

        @Test
        @DisplayName("Should validate payment to income ratio")
        void shouldValidatePaymentToIncomeRatio() {
            affiliate.setSalary(3000.0);

            // 40% of 3000 = 1200, so payment of 1000 should be valid
            assertTrue(affiliate.isPaymentToIncomeRatioValid(1000.0));

            // Payment of 1500 exceeds 40%, should be invalid
            assertFalse(affiliate.isPaymentToIncomeRatioValid(1500.0));
        }
    }
}
