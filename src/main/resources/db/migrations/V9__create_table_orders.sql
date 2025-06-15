CREATE TABLE IF NOT EXISTS trade.orders (
                                            id bigserial PRIMARY KEY,
                                            user_id bigint NOT NULL,
                                            product_id bigint NOT NULL,
                                            quantity int NOT NULL,
                                            total numeric(10, 2) DEFAULT 0.00,
                                            status varchar(50) NOT NULL,
                                            txid varchar(500),
                                            qr_code TEXT,
                                            qr_code_image TEXT,
                                            created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                                            payment_date timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES security.users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_order_product FOREIGN KEY (product_id) REFERENCES trade.products(id) ON DELETE RESTRICT
    );