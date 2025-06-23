package com.gec.ecommerce.dto.request;

public record OrderRequest(
        Long userId,
        Long productId,
        int quantity,
        String total,
        Long idCart) {
}
