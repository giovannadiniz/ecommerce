package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CartRequest (
        @NotBlank
        Long userId,
        @NotBlank
        Long productId,
        @NotBlank
        String productName,
        @NotBlank
        Integer quantity
) {}