package com.gec.ecommerce.filter;

import java.math.BigDecimal;

public record CartFilter(
        Long id,
        Long userId,
        Long productId,
        BigDecimal total
) {
    public CartFilter() {
        this(null, null, null, null);
    }
}