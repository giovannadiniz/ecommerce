CREATE TABLE IF NOT EXISTS trade.products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    active BOOLEAN DEFAULT TRUE
);