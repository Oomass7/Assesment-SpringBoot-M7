-- V1__create_schema.sql
-- Initial schema creation for Auth Service

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'AFILIADO' CHECK (role IN ('AFILIADO', 'ANALISTA', 'ADMIN')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);
CREATE INDEX IF NOT EXISTS idx_users_active ON users(active);

-- Add comments for documentation
COMMENT ON TABLE users IS 'User accounts for authentication and authorization';
COMMENT ON COLUMN users.email IS 'Unique email address used for login';
COMMENT ON COLUMN users.password IS 'BCrypt encrypted password';
COMMENT ON COLUMN users.name IS 'Full name of the user';
COMMENT ON COLUMN users.role IS 'User role: AFILIADO, ANALISTA, or ADMIN';
COMMENT ON COLUMN users.created_at IS 'Timestamp when user was created';
COMMENT ON COLUMN users.active IS 'Whether the user account is active';
