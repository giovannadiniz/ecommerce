CREATE TABLE trade.carts (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             product_id BIGINT NOT NULL,
                             quantity INTEGER NOT NULL DEFAULT 1,
                             total NUMERIC(19,2) NOT NULL,
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

                             CONSTRAINT fk_cart_user FOREIGN KEY (user_id) REFERENCES security.users(id),
                             CONSTRAINT fk_cart_product FOREIGN KEY (product_id) REFERENCES trade.products(id),
                             CONSTRAINT uk_cart_user UNIQUE (user_id)
);