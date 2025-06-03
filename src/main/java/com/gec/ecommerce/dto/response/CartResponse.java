package com.gec.ecommerce.dto.response;

import com.gec.ecommerce.domain.User;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {

    private User userId;
    private BigDecimal total;
    private List<CartItemResponse> items;

}
