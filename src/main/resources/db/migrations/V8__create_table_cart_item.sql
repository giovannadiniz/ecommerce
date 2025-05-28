CREATE TABLE IF NOT EXISTS trade.cart_items (
        id bigserial PRIMARY KEY,
        cart_id bigint NOT NULL,
        product_id bigint NOT NULL,
        quantity integer NOT NULL CHECK (quantity > 0),
        subtotal numeric(10, 2) NOT NULL,
        created_at timestamp DEFAULT CURRENT_TIMESTAMP,
        updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_cart_item_cart FOREIGN KEY (cart_id) REFERENCES trade.carts(id) ON DELETE CASCADE,
        CONSTRAINT fk_cart_item_product FOREIGN KEY (product_id) REFERENCES trade.products(id) ON DELETE CASCADE
    );