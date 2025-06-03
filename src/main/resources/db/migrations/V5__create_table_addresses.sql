CREATE TABLE IF NOT EXISTS trade.addresses(
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    cep VARCHAR(8) NOT NULL,
    complement VARCHAR(100),
    logradouro VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    number VARCHAR(2) NOT NULL,

    CONSTRAINT fk_address_user FOREIGN KEY (user_id) REFERENCES security.users(id) ON DELETE RESTRICT DEFERRABLE INITIALLY DEFERRED
);