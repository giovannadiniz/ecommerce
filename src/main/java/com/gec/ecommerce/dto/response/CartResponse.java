package com.gec.ecommerce.dto.response;

import java.math.BigDecimal;

public class CartResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;

    // Construtor padrão
    public CartResponse() {}

    // Construtor completo
    public CartResponse(Long id, Long userId, Long productId, String productName,
                        BigDecimal productPrice, Integer quantity, BigDecimal total) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.price = productPrice;
        this.quantity = quantity;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal productPrice) {
        this.price = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}