package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.dto.CouponShallowDto;
import com.gec.ecommerce.dto.request.CouponRequest;
import com.gec.ecommerce.dto.response.CouponResponse;
import com.gec.ecommerce.filter.CouponFilter;
import com.gec.ecommerce.mapper.CouponMapper;
import com.gec.ecommerce.service.CouponService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/coupon")
public class CouponController extends BaseController<Coupon, CouponFilter, CouponShallowDto, CouponRequest, CouponResponse, CouponService> {

    private final CouponMapper couponMapper;

    private final CouponService couponService;

    public CouponController(CouponService couponService, CouponMapper couponMapper) {

        this.couponService = couponService;
        this.couponMapper = couponMapper;
    }

    @Override
    protected CouponService getEntityService() {

        return this.couponService;
    }

    @Override
    public ResponseEntity<BasePaginatedResponse<CouponShallowDto>> listAll(Integer page, Integer size, CouponFilter couponFilter, HttpServletRequest request) {

        return ResponseEntity.ok().body(this.couponService.listAll(page,size,couponFilter,request));
    }

    @Override
    public ResponseEntity<CouponResponse> findEntityById(Long id) {
        return ResponseEntity.ok().body(this.couponService.findCouponById(id));
    }

    @Override
    public ResponseEntity<CouponResponse> createNew(@Valid @RequestBody CouponRequest couponRequest) {
        Coupon coupon = couponService.saveWithReturn(couponMapper.requestToEntity(couponRequest));
        return ResponseEntity.ok().body(couponMapper.entityToResponse(coupon));
    }

    @Override
    public ResponseEntity<CouponResponse> update(@Valid @RequestBody CouponRequest couponRequest, Long id) {
        CouponResponse updatedCoupon = couponService.update(id, couponRequest);
        return ResponseEntity.ok(updatedCoupon);
    }

    public ResponseEntity<String> delete(Long id) {
        couponService.delete(id);
        return ResponseEntity.ok().build();
    }

}
