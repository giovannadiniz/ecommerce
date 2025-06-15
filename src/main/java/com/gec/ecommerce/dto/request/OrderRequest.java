package com.gec.ecommerce.dto.request;

import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;

public record OrderRequest(
        Long userId,
        Long productId,
        int quantity,
        BigDecimal total,
        Long idCart) {
}
