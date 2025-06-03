CREATE TABLE IF NOT EXISTS trade.order_items (
                                          id bigserial PRIMARY KEY,
                                          order_id bigint NOT NULL,
                                          product_id bigint NOT NULL,
                                          quantity integer NOT NULL CHECK (quantity > 0),
                                          unit_price numeric(10, 2) NOT NULL,
                                          subtotal numeric(10, 2) NOT NULL,
                                          created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES trade.orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES trade.products(id) ON DELETE RESTRICT
    );
