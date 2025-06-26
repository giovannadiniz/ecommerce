package com.gec.ecommerce.dto;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;
import java.util.List;

public record CartShallowDto(
        BigDecimal total
){
}
