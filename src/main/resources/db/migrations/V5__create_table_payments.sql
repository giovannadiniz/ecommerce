CREATE TABLE IF NOT EXISTS security.payments (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    paid_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);