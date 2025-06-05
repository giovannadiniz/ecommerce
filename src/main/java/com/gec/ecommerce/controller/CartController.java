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
import com.gec.ecommerce.service.TokenService;
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
    private final TokenService tokenService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, TokenService tokenService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.tokenService = tokenService;
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

    @PostMapping("/my-cart")
    public ResponseEntity<CartResponse> addToMyCart(
            @Valid @RequestBody CartRequest cartRequest,
            HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);

            // Cria novo CartRequest com userId extraído do token
            CartRequest requestWithUserId = new CartRequest(userId, cartRequest.productId(), cartRequest.quantity());

            Cart cart = cartService.createOrUpdateCart(requestWithUserId);
            return ResponseEntity.ok(cartMapper.entityToResponse(cart));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null); // Ou retorne uma mensagem de erro
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Busca carrinho do usuário logado (via JWT)
     */
    @GetMapping("/my-cart")
    public ResponseEntity<CartResponse> getMyCart(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            CartResponse cart = cartService.findCartByUserId(userId);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza carrinho do usuário logado (via JWT)
     */
    @PutMapping("/my-cart")
    public ResponseEntity<CartResponse> updateMyCart(
            @Valid @RequestBody CartRequest cartRequest,
            HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);

            // Primeiro, busca o carrinho existente para pegar o ID
            CartResponse existingCart = cartService.findCartByUserId(userId);

            // Atualiza usando o ID do carrinho
            CartResponse updatedCart = cartService.updateCart(existingCart.id(), cartRequest);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove carrinho do usuário logado (via JWT)
     */
    @DeleteMapping("/my-cart")
    public ResponseEntity<Void> deleteMyCart(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            cartService.deleteByUserId(userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Limpa carrinho do usuário logado (via JWT)
     */
    @PostMapping("/my-cart/clear")
    public ResponseEntity<Void> clearMyCart(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            cartService.clearCart(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Verifica se usuário logado tem carrinho (via JWT)
     */
    @GetMapping("/my-cart/exists")
    public ResponseEntity<Boolean> hasMyCart(HttpServletRequest request) {
        try {
            Long userId = getUserIdFromToken(request);
            boolean hasCart = cartService.hasCart(userId);
            return ResponseEntity.ok(hasCart);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }

}