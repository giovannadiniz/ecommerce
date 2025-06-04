package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CartItemRequest(
        @NotNull(message = "O ID do produto é obrigatório")
        Long productId, // ID do produto a ser adicionado

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantity,

        BigDecimal subtotal)// Quantidade desejada)
         {}
