-- V2__initial_data.sql
-- Insert initial admin user for system setup
-- Password is BCrypt encrypted: 'admin123' -> '$2a$10$...'

-- Note: In production, you would use a properly encrypted password
-- This is just for development/testing purposes
-- The password 'Admin123!' is encrypted with BCrypt

INSERT INTO users (email, password, name, role, active) VALUES
    ('admin@creditapp.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MG9HQAL8/D8hV3gGN9E9x7xnPbp5JhO', 'System Administrator', 'ADMIN', true),
    ('analyst@creditapp.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MG9HQAL8/D8hV3gGN9E9x7xnPbp5JhO', 'Credit Analyst', 'ANALISTA', true),
    ('affiliate@creditapp.com', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MG9HQAL8/D8hV3gGN9E9x7xnPbp5JhO', 'Test Affiliate', 'AFILIADO', true);

-- Note: All test users have password 'password' (BCrypt hash shown above)
