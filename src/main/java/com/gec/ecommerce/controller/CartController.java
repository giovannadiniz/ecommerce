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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.hibernate.service.spi.ServiceException;

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
    public ResponseEntity<BasePaginatedResponse<CartShallowDto>> listAll(
            Integer page, Integer size, CartFilter cartFilter, HttpServletRequest request) {
        return ResponseEntity.ok().body(this.cartService.listAll(page, size, cartFilter, request));
    }

    @Override
    public ResponseEntity<CartResponse> findEntityById(Long id) {
        return ResponseEntity.ok().body(this.cartService.findCartById(id));
    }

    @Override
    public ResponseEntity<CartResponse> createNew(@Valid @RequestBody CartRequest cartRequest) throws ServiceException {
        try {
            Cart cart = cartService.createOrUpdateCart(cartRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.entityToResponse(cart));
        } catch (RuntimeException e) {
            throw new ServiceException("Error creating cart: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<CartResponse> update(@Valid @RequestBody CartRequest cartRequest, Long id) throws ServiceException {
        try {
            CartResponse updatedCart = cartService.updateCart(id, cartRequest);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            throw new ServiceException("Error updating cart: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return super.delete(id); // Usando implementação da base
    }

    /**
     * Busca carrinho por usuário
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponse> findByUserId(@PathVariable Long userId) {
        try {
            CartResponse cart = cartService.findCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verifica se usuário tem carrinho
     */
    @GetMapping("/user/{userId}/exists")
    public ResponseEntity<Boolean> hasCart(@PathVariable Long userId) {
        boolean hasCart = cartService.hasCart(userId);
        return ResponseEntity.ok(hasCart);
    }

    /**
     * Remove carrinho por usuário
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId) {
        cartService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Limpa carrinho (remove produto mas mantém carrinho)
     */
    @PostMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para criar/atualizar carrinho de forma mais específica
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<CartResponse> createOrUpdateForUser(
            @PathVariable Long userId,
            @Valid @RequestBody CartRequest cartRequest) {

        // Força o userId da URL
        CartRequest requestWithUserId = new CartRequest(userId, cartRequest.productId(), cartRequest.quantity());

        try {
            Cart cart = cartService.createOrUpdateCart(requestWithUserId);
            return ResponseEntity.ok(cartMapper.entityToResponse(cart));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}