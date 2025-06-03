package com.gec.ecommerce.dto;

import java.math.BigDecimal;

public record CartItemShallowDto(
        Long id,               // ID do item no carrinho
        Long productId,        // ID do produto associado
        String productName,    // Nome do produto (opcional)
        Integer quantity,      // Quantidade do item
        BigDecimal unitPrice,  // Preço unitário (pode vir do Product)
        BigDecimal subtotal    // unitPrice * quantity
 ) {}
