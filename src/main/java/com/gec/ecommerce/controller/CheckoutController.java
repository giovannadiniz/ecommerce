package com.gec.ecommerce.controller;

import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.mapper.OrderMapper;
import com.gec.ecommerce.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("/processar")
    public ResponseEntity<OrderResponse> processarCheckout(@Valid @RequestBody OrderRequest request) {
         Order order = checkoutService.processarCheckout(request);
         return ResponseEntity.ok().body(orderMapper.entityToResponse(order));
    }

    @GetMapping("/meusPedidos")
    public ResponseEntity<List<OrderResponse>> findMyOrders() {
        try {
            User authenticatedUser = getAuthenticatedUser();
            List<OrderResponse> orders = checkoutService.findOrdersByUserId(authenticatedUser.getId());

            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pesquisarOrder")
    public ResponseEntity<OrderResponse> findOrders() {
        try {
            User authenticatedUser = getAuthenticatedUser();

            Optional<OrderResponse> order = checkoutService.findOrderByUserIdOptional(authenticatedUser.getId());

            if (order.isPresent()) {
                return ResponseEntity.ok(order.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

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
}

