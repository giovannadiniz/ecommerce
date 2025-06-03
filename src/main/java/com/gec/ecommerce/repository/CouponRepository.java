package com.gec.ecommerce.repository;

import com.gec.ecommerce.bases.BaseRepository;
import com.gec.ecommerce.domain.Coupon;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends BaseRepository<Coupon> {
}