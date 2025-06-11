package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.request.AddToCartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import com.gec.ecommerce.mapper.CartMapper;
import com.gec.ecommerce.service.CartService;
import com.gec.ecommerce.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return super.delete(id);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> removerCart(@PathVariable Long id) throws ServiceException {
        try {
            User authenticatedUser = getAuthenticatedUser();
            CartResponse cart = cartService.findCartByUserId(authenticatedUser.getId());
            if (!cart.getId().equals(id)) {
                throw new ServiceException("Unauthorized: Cart ID does not belong to the authenticated user");
            }
            cartService.delete(id);
            return ResponseEntity.ok("Cart deleted successfully");
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to delete cart: " + e.getMessage(), e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request) throws ServiceException {
        try {
            User authenticatedUser = getAuthenticatedUser();

            CartRequest cartRequest = new CartRequest(
                    authenticatedUser.getId(),
                    request.productId(),
                    request.productName(),
                    request.quantity()
            );

            Cart cart = cartService.createOrUpdateCart(cartRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartMapper.entityToResponse(cart));
        } catch (RuntimeException e) {
            throw new ServiceException("Error adding product to cart: " + e.getMessage(), e);
        }
    }

    /**
     * Obtém o carrinho do usuário autenticado
     */
    @GetMapping("/my-cart")
    public ResponseEntity<CartResponse> getMyCart() {
        try {
            User authenticatedUser = getAuthenticatedUser();

            Optional<CartResponse> cart = cartService.findCartByUserIdOptional(authenticatedUser.getId());

            if (cart.isPresent()) {
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Atualiza a quantidade de um produto no carrinho do usuário autenticado
     */
    @PutMapping("/update-quantity")
    public ResponseEntity<CartResponse> updateQuantity(@Valid @RequestBody AddToCartRequest request) throws ServiceException {
        try {
            User authenticatedUser = getAuthenticatedUser();

            CartResponse existingCart = cartService.findCartByUserId(authenticatedUser.getId());

            CartRequest cartRequest = new CartRequest(
                    authenticatedUser.getId(),
                    request.productId(),
                    request.productName(),
                    request.quantity()
            );

            CartResponse updatedCart = cartService.updateCart(existingCart.getId(), cartRequest);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            throw new ServiceException("Failed to update cart quantity: " + e.getMessage(), e);
        }
    }

    /**
     * Remove o carrinho do usuário autenticado
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearMyCart() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            cartService.clearCart(authenticatedUser.getId());
            return ResponseEntity.ok("Cart cleared successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error clearing cart: " + e.getMessage());
        }
    }

    /**
     * Remove completamente o carrinho do usuário autenticado
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeMyCart() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            cartService.deleteByUserId(authenticatedUser.getId());
            return ResponseEntity.ok("Cart removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error removing cart: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para obter o usuário autenticado do contexto de segurança
     */
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User)) {
            throw new RuntimeException("Invalid authentication principal");
        }

        return (User) principal;
    }

    /**
     * Método alternativo para extrair usuário do token no header (caso necessário)
     */
    private User getUserFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing authorization header");
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        String username = tokenService.validateToken(token);

        if (username == null) {
            throw new RuntimeException("Invalid token");
        }

        // Aqui você precisaria buscar o usuário pelo username
        // Este é um exemplo - você deve implementar conforme sua lógica
        // User user = userService.findByUsername(username);
        // return user;

        throw new RuntimeException("Method not fully implemented - use getAuthenticatedUser() instead");
    }
}