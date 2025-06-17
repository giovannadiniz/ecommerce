package com.gec.ecommerce.dto.response;

import java.math.BigDecimal;

public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private java.math.BigDecimal total;
    private String qrCode;
    private String qrCodeImage;
    private String status;

    public OrderResponse() {}

    public OrderResponse(Long orderId, Long userId, Long productId, Integer quantity, java.math.BigDecimal total, String qrCode, String qrCodeImage, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.qrCode = qrCode;
        this.qrCodeImage = qrCodeImage;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public java.math.BigDecimal getTotal() {
        return total;
    }

    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(String qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}