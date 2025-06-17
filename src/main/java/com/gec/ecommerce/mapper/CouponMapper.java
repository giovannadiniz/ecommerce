package com.gec.ecommerce.mapper;

import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.dto.CouponShallowDto;
import com.gec.ecommerce.dto.request.CouponRequest;
import com.gec.ecommerce.dto.response.CouponResponse;
import com.gec.ecommerce.filter.CouponFilter;
import org.springframework.stereotype.Component;
import org.mapstruct.*;


@Component
@Mapper(componentModel = "spring")
public abstract class CouponMapper extends BaseMapper<Coupon, CouponFilter, CouponShallowDto, CouponRequest, CouponResponse>
{}
