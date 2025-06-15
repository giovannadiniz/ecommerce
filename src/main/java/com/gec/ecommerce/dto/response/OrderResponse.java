package com.gec.ecommerce.dto.response;

import java.math.BigDecimal;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Integer quantity;
    private BigDecimal total;

    public OrderResponse() {}

    public OrderResponse(Long id, Long userId, Integer quantity, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.quantity = quantity;
        this.total = total;
    }
}
