package com.gec.ecommerce.dto;

import java.math.BigDecimal;

public record CouponShallowDto(
        Long id,
        String code,
        BigDecimal discount
        ){}
