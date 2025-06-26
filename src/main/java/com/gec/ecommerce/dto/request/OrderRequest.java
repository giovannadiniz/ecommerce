package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrderRequest(
        @NotBlank
        Long userId,
        @NotBlank
        Long productId,
        @NotBlank
        int quantity,
        @NotBlank
        String total,
        @NotBlank
        Long idCart) {
}
