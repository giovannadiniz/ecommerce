package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import com.gec.ecommerce.mapper.CartMapper;
import com.gec.ecommerce.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
public class CartController extends BaseController<Cart, CartFilter, CartShallowDto, CartRequest, CartResponse, CartService> {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @Override
    protected CartService getEntityService() {
        return this.cartService;
    }

    @Override
    public ResponseEntity<BasePaginatedResponse<CartShallowDto>> listAll(Integer page, Integer size, CartFilter cartFilter, HttpServletRequest request) {
        return ResponseEntity.ok().body(this.cartService.listAll(page, size, cartFilter, request));
    }

    @Override
    public ResponseEntity<CartResponse> findEntityById(Long id) {
        return ResponseEntity.ok().body(this.cartService.findCartById(id));
    }

    @Override
    public ResponseEntity<CartResponse> createNew(@Valid @RequestBody CartRequest cartRequest){
        Cart cart = cartService.saveWithReturn(cartMapper.requestToEntity(cartRequest));
        return ResponseEntity.ok().body(cartMapper.entityToResponse(cart));
    }

    @Override
    public ResponseEntity<CartResponse> update(@Valid @RequestBody CartRequest cartRequest, Long id){
        CartResponse updatedCart = cartService.updateCart(id, cartRequest);
        return ResponseEntity.ok(updatedCart);
    }

    public ResponseEntity<String> delete(Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
