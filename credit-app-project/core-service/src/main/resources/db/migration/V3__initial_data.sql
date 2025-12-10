-- V3__initial_data.sql
-- Insert initial test data for development and testing

-- Insert sample affiliates with different statuses and seniority
INSERT INTO affiliates (document, name, salary, affiliation_date, status) VALUES
    ('12345678A', 'Juan García López', 3500.00, '2023-01-15', 'ACTIVE'),
    ('23456789B', 'María Rodríguez Martín', 4200.00, '2022-06-01', 'ACTIVE'),
    ('34567890C', 'Carlos Fernández Ruiz', 2800.00, '2024-08-01', 'ACTIVE'),  -- Less than 6 months
    ('45678901D', 'Ana Martínez García', 5500.00, '2021-03-20', 'INACTIVE'),
    ('56789012E', 'Pedro Sánchez Díaz', 3800.00, '2023-09-01', 'ACTIVE');

-- Insert sample credit requests with different statuses
INSERT INTO requests (affiliate_id, client_document, client_name, requested_amount, term_months, proposed_rate, status, risk_score, risk_level, request_date, evaluation_date, rejection_reason) VALUES
    (1, '12345678A', 'Juan García López', 15000.00, 24, 0.12, 'APPROVED', 250, 'LOW', '2024-01-10 10:30:00', '2024-01-10 10:31:00', NULL),
    (2, '23456789B', 'María Rodríguez Martín', 25000.00, 36, 0.14, 'PENDING', 550, 'MEDIUM', '2024-11-15 14:00:00', NULL, NULL),
    (2, '23456789B', 'María Rodríguez Martín', 10000.00, 12, 0.10, 'APPROVED', 200, 'LOW', '2024-06-20 09:15:00', '2024-06-20 09:16:00', NULL),
    (5, '56789012E', 'Pedro Sánchez Díaz', 50000.00, 48, 0.16, 'REJECTED', 850, 'HIGH', '2024-10-05 11:45:00', '2024-10-05 11:46:00', 'High risk level'),
    (1, '12345678A', 'Juan García López', 8000.00, 18, 0.11, 'PENDING', NULL, NULL, '2024-12-01 16:30:00', NULL, NULL);
