-- V1__create_schema.sql
-- Initial schema creation for Core Service

-- Affiliates table
CREATE TABLE IF NOT EXISTS affiliates (
    id BIGSERIAL PRIMARY KEY,
    document VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    salary DECIMAL(15,2) NOT NULL CHECK (salary > 0),
    affiliation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE'))
);

-- Credit requests table
CREATE TABLE IF NOT EXISTS requests (
    id BIGSERIAL PRIMARY KEY,
    affiliate_id BIGINT,
    client_document VARCHAR(50) NOT NULL,
    client_name VARCHAR(255) NOT NULL,
    requested_amount DECIMAL(15,2) NOT NULL CHECK (requested_amount > 0),
    term_months INTEGER NOT NULL CHECK (term_months > 0),
    proposed_rate DECIMAL(5,4),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    risk_score INTEGER,
    risk_level VARCHAR(20) CHECK (risk_level IN ('LOW', 'MEDIUM', 'HIGH', 'BAJO', 'MEDIO', 'ALTO')),
    request_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    evaluation_date TIMESTAMP,
    rejection_reason VARCHAR(500)
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_affiliates_document ON affiliates(document);
CREATE INDEX IF NOT EXISTS idx_affiliates_status ON affiliates(status);
CREATE INDEX IF NOT EXISTS idx_requests_affiliate_id ON requests(affiliate_id);
CREATE INDEX IF NOT EXISTS idx_requests_client_document ON requests(client_document);
CREATE INDEX IF NOT EXISTS idx_requests_status ON requests(status);
CREATE INDEX IF NOT EXISTS idx_requests_request_date ON requests(request_date);
