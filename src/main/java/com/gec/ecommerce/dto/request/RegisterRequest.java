package com.gec.ecommerce.dto.request;

public record RegisterRequest(
        String firstName,
        String username,
        String email,
        String password,
        String role,
        String phone
) {}
