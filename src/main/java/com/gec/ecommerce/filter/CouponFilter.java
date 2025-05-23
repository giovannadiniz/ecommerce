package com.gec.ecommerce.filter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponFilter(
        Long id,
        LocalDateTime createdAt,
        String code,
        BigDecimal discount,
        LocalDateTime expirationDate
){
}
