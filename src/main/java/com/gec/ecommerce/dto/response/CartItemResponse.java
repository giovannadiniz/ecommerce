package com.gec.ecommerce.dto.response;

import java.math.BigDecimal;

public class CartItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal subtotal;
    private BigDecimal price;
}
