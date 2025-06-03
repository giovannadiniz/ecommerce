CREATE TABLE IF NOT EXISTS trade.carts(
                      id BIGSERIAL PRIMARY KEY,
                      user_id BIGINT NOT NULL,
                      total DECIMAL(10,2),
        CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES security.users(id) ON DELETE CASCADE
);