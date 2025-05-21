package com.gec.ecommerce.filter;

import java.math.BigDecimal;

public record ProductFilter(
        Long id,
        String name,
        BigDecimal price,
        Boolean active
        ){}
