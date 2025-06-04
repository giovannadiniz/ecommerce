package com.gec.ecommerce.dto;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;
import java.util.List;

public record CartShallowDto(         // ID do usuário/dono do carrinho
        BigDecimal total       // Valor total do carrinho
){
}
