CREATE TABLE IF NOT EXISTS trade.adresses(
    id bigserial PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    complement VARCHAR(100),
    logradouro VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    number VARCHAR(2) NOT NULL
);