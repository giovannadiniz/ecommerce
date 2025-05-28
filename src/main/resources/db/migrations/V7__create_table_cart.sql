CREATE TABLE IF NOT EXISTS trade.carts(
                      id BIGSERIAL PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      total numeric(10, 2) DEFAULT 0.00,
        CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES security.users(id) ON DELETE CASCADE
);