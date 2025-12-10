-- V2__add_relations.sql
-- Add foreign key relationships between tables

-- Add foreign key constraint from requests to affiliates
ALTER TABLE requests
ADD CONSTRAINT fk_requests_affiliate
FOREIGN KEY (affiliate_id)
REFERENCES affiliates(id)
ON DELETE SET NULL;

-- Add comments to tables and columns for documentation
COMMENT ON TABLE affiliates IS 'Stores affiliate (member) information for the credit cooperative';
COMMENT ON COLUMN affiliates.document IS 'Unique identification document (DNI, passport, etc.)';
COMMENT ON COLUMN affiliates.name IS 'Full name of the affiliate';
COMMENT ON COLUMN affiliates.salary IS 'Monthly salary in local currency';
COMMENT ON COLUMN affiliates.affiliation_date IS 'Date when the person became an affiliate';
COMMENT ON COLUMN affiliates.status IS 'Current status: ACTIVE or INACTIVE';

COMMENT ON TABLE requests IS 'Credit requests submitted by affiliates';
COMMENT ON COLUMN requests.affiliate_id IS 'Reference to the affiliate who made the request';
COMMENT ON COLUMN requests.client_document IS 'Document of the client (may differ from affiliate for joint requests)';
COMMENT ON COLUMN requests.client_name IS 'Name of the client';
COMMENT ON COLUMN requests.requested_amount IS 'Amount of credit requested';
COMMENT ON COLUMN requests.term_months IS 'Repayment term in months';
COMMENT ON COLUMN requests.proposed_rate IS 'Proposed annual interest rate (decimal format, e.g., 0.12 for 12%)';
COMMENT ON COLUMN requests.status IS 'Request status: PENDING, APPROVED, or REJECTED';
COMMENT ON COLUMN requests.risk_score IS 'Risk score from risk service (0-1000)';
COMMENT ON COLUMN requests.risk_level IS 'Calculated risk level: LOW, MEDIUM, or HIGH';
COMMENT ON COLUMN requests.request_date IS 'Timestamp when request was created';
COMMENT ON COLUMN requests.evaluation_date IS 'Timestamp when request was evaluated';
COMMENT ON COLUMN requests.rejection_reason IS 'Reason for rejection if applicable';
