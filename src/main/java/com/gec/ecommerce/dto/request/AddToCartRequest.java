package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para adicionar produtos ao carrinho
 * Não inclui userId pois será extraído do token JWT
 */
public record AddToCartRequest(
        @NotNull(message = "Product ID is required")
        Long productId,

        @NotBlank(message = "Product name is required")
        String productName,

        @Positive(message = "Quantity must be positive")
        Integer quantity
) {
}