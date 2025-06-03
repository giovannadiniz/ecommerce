ALTER TABLE security.payments DROP CONSTRAINT IF EXISTS fk_payment_order;

ALTER TABLE security.payments
    ADD CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id) REFERENCES trade.orders(id)
            ON DELETE RESTRICT
            DEFERRABLE INITIALLY DEFERRED;