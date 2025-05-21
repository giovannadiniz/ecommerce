package com.gec.ecommerce.dto;

import java.math.BigDecimal;

public record ProductShallowDto(
        Long id,
        String name,
        BigDecimal price
        ){}
