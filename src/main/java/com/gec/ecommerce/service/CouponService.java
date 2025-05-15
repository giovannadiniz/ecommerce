package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.dto.CouponShallowDto;
import com.gec.ecommerce.dto.request.CouponRequest;
import com.gec.ecommerce.dto.response.CouponResponse;
import com.gec.ecommerce.exception.CouponNotFoundException;
import com.gec.ecommerce.filter.CouponFilter;
import com.gec.ecommerce.mapper.CouponMapper;
import com.gec.ecommerce.repository.CouponRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CouponService extends BaseService<Coupon, CouponFilter> {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponService(CouponRepository couponRepository, CouponMapper couponMapper) {

        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public JpaRepository<Coupon, Long> getRepository() {

        return couponRepository;
    }

    @Transactional
    @Override
    public Coupon saveWithReturn(Coupon coupon) {

        return getRepository().save(coupon);
    }


    public BasePaginatedResponse<CouponShallowDto> listAll(Integer page, Integer size, CouponFilter couponFilter, HttpServletRequest request){
        UriComponentsBuilder uri = UriComponentsBuilder.fromPath(request.getServletPath()).query(request.getQueryString());

        Page<Coupon> couponPage = findAll(page,size,couponFilter);

        return new BasePaginatedResponse<>(couponPage.map(couponMapper::entityToShallowDto),uri);
    }


    @Override
    public Page<Coupon> findAll(int page, int size, CouponFilter couponFilter) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Specification<Coupon> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (couponFilter.code() != null)
                predicates.add(cb.equal(root.get("code"), couponFilter.code()));
            if (couponFilter.createdAt() != null)
                predicates.add(cb.equal(root.get("createdAt"), couponFilter.createdAt()));
            if (couponFilter.discount() != null)
                predicates.add(cb.equal(root.get("amount"), couponFilter.discount()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return couponRepository.findAll(spec, pageable);
    }

    public CouponResponse findCouponById(Long id) {
        Coupon coupon = findById(id).orElseThrow(() -> new CouponNotFoundException(id));
        return couponMapper.entityToResponse(coupon);
    }


    @Transactional
    public CouponResponse update(Long id, CouponRequest request) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        var couponUpdated = couponMapper.requestToEntity(request);

        BeanUtils.copyProperties(couponUpdated, existingCoupon, "id");

        var updatedCoupon = couponRepository.save(existingCoupon);

        return couponMapper.entityToResponse(updatedCoupon);
    }


    @Transactional
    public void delete(Long id) {
        Optional<Coupon> coupon = findById(id);
        coupon.ifPresentOrElse(
                couponRepository::delete,
                () -> { throw new CouponNotFoundException(id); }
                );
    }
}
