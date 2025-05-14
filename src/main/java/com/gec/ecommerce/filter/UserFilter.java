package com.gec.ecommerce.filter;

public record UserFilter(
        Long id,
        String username,
        String email,
        String phone
) {
}
