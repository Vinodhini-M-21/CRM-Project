
CREATE DATABASE IF NOT EXISTS crm_db;
USE crm_db;

-- ---------- CUSTOMERS TABLE ----------
CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    company VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------- LEADS TABLE ----------
CREATE TABLE IF NOT EXISTS leads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    status VARCHAR(50) DEFAULT 'New',
    source VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------- FOLLOWUPS TABLE ----------
CREATE TABLE IF NOT EXISTS followups (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    followup_date DATE,
    notes VARCHAR(255),
    status VARCHAR(50) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ---------- SAMPLE DATA ----------
INSERT INTO customers (name, email, phone, company) VALUES
('Ravi Kumar', 'ravi@example.com', '9876543210', 'ABC Traders'),
('Priya Sharma', 'priya@example.com', '9123456780', 'XYZ Corp');

INSERT INTO leads (name, email, phone, status, source) VALUES
('Arun Vel', 'arun@example.com', '9988776655', 'New', 'Website'),
('Divya Rani', 'divya@example.com', '9090909090', 'Contacted', 'Referral');

INSERT INTO followups (customer_name, followup_date, notes, status) VALUES
('Ravi Kumar', '2026-07-10', 'Discuss contract renewal', 'Pending'),
('Priya Sharma', '2026-07-12', 'Send product catalog', 'Completed');
