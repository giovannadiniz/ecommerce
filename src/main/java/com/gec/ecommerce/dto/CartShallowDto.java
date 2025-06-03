package com.gec.ecommerce.dto;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;
import java.util.List;

public record CartShallowDto(
        Long id,                   // ID do carrinho
        User userId,               // ID do usuário/dono do carrinho
        BigDecimal total,          // Valor total do carrinho
        List<CartItemShallowDto> items
){
}
