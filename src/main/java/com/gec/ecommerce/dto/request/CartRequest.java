package com.gec.ecommerce.dto.request;

public record CartRequest (
        Long userId,
        Long productId,
        String productName,
        Integer quantity
) {}