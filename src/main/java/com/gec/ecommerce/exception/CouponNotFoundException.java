package com.gec.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CouponNotFoundException extends RuntimeException {

    private final Long couponId;

    public CouponNotFoundException(Long couponId) {
        super("coupon with id: " + couponId + " not found");
        this.couponId = couponId;
    }

    public Long getCouponId() {
        return couponId;
    }

}