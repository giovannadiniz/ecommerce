package com.gec.ecommerce.dto.response;

import java.math.BigDecimal;

public class CouponResponse {

    private String code;
    private BigDecimal discount;

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
