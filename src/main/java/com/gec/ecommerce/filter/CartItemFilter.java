package com.gec.ecommerce.filter;

public record CartItemFilter(
        Long id,
        Long cartId,
        Long productId
) {
}
