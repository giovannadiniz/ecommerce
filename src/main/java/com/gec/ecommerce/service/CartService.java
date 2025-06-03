package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import com.gec.ecommerce.mapper.CartMapper;
import com.gec.ecommerce.repository.CartRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService extends BaseService<Cart, CartFilter> {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public JpaRepository<Cart, Long> getRepository() {
        return cartRepository;
    }

    @Transactional
    @Override
    public Cart saveWithReturn(Cart cart) {
        return getRepository().save(cart);
    }

    public BasePaginatedResponse<CartShallowDto> listAll(Integer page, Integer size, CartFilter cartFilter, HttpServletRequest request){
        UriComponentsBuilder uri = UriComponentsBuilder.fromPath(request.getServletPath()).query(request.getQueryString());

        Page<Cart> cartPage = findAll(page,size,cartFilter);

        return new BasePaginatedResponse<>(cartPage.map(cartMapper::entityToShallowDto),uri);
    }

    @Override
    public Page<Cart> findAll(int page, int size, CartFilter cartFilter) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Specification<Cart> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cartFilter.id() != null)
                predicates.add(cb.equal(root.get("id"), cartFilter.id()));
            if (cartFilter.user() != null)
                predicates.add(cb.equal(root.get("user"), cartFilter.user()));
            if (cartFilter.total() != null)
                predicates.add(cb.equal(root.get("total"), cartFilter.total()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return cartRepository.findAll(spec, pageable);
    }

    public CartResponse findCartById(Long id) {
        Cart cart = findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.entityToResponse(cart);
    }

    @Transactional
    public CartResponse updateCart(Long id, CartRequest request) {
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        var cartUpdated = cartMapper.requestToEntity(request);

        BeanUtils.copyProperties(cartUpdated, existingCart, "id");

        var updatedCart = cartRepository.save(existingCart);

        return cartMapper.entityToResponse(updatedCart);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Cart> cart = findById(id);
        cart.ifPresentOrElse(
                cartRepository::delete,
                () -> { throw new RuntimeException("Cart not found"); }
        );
    }
}
