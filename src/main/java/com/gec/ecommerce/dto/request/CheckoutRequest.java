package com.gec.ecommerce.dto.request;

public record CheckoutRequest (
        Long idProduct,
        int total,
        Long idCart)
        {}
