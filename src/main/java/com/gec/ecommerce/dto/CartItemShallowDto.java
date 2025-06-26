package com.gec.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemShallowDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
 ) {}
