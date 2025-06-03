package com.gec.ecommerce.filter;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;

public record CartFilter(
        Long id,
        User user,
        BigDecimal total
) {
}
