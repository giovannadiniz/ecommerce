CREATE TABLE IF NOT EXISTS trade.orders(
                               id bigserial PRIMARY KEY,
                               user_id bigint NOT NULL,
                               address_id bigint NOT NULL,
                               payment_id bigint NOT NULL,
                               total numeric(10, 2) DEFAULT 0.00,
                               created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                                CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES security.users(id) ON DELETE RESTRICT,
                                CONSTRAINT fk_order_address FOREIGN KEY (address_id) REFERENCES trade.adresses(id) ON DELETE RESTRICT,
                                CONSTRAINT fk_order_payment FOREIGN KEY (payment_id) REFERENCES security.payments(id) ON DELETE RESTRICT
    );