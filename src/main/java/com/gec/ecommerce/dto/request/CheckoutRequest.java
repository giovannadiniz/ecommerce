package com.gec.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest (
        @NotBlank
        Long idProduct,
        @NotBlank
        int total,
        @NotBlank
        Long idCart)
        {}
