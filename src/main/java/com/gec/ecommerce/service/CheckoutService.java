package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.dto.response.ProductResponse;
import com.gec.ecommerce.filter.OrderFilter;
import com.gec.ecommerce.mapper.OrderMapper;
import com.gec.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckoutService extends BaseService<Order, OrderFilter> {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PixService pixService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public Order processarCheckout(OrderRequest request) {
            User user = userService.findById(request.userId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Product product = productService.findById(request.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            String chaveJson = pixService.listarEVPs();
            String pixResponse = pixService.pixCreateCharge(chaveJson, request.total());

            Order order = createOrder(user, product, request, pixResponse);

            order = orderRepository.save(order);


            cartService.delete(request.idCart());

            return order;
    }

    public List<OrderResponse> findOrdersByUserId(Long userId) {
        logger.info("Finding all orders for user ID: " + userId);
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);

        return orders.stream()
                .map(orderMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponse> findOrderByUserIdOptional(Long userId) {
        logger.info("Finding order by user ID (optional): " + userId);
        return orderRepository.findByUserId(userId)
                .map(orderMapper::entityToResponse);
    }

    private Order createOrder(User user, Product product, OrderRequest request, String pixResponse) {
        BigDecimal total = new BigDecimal(request.total());

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setStatus("pendente");
        order.setQuantity(request.quantity() > 0 ? request.quantity() : 1);
        order.setTotal(total);
        order.setQrCode(pixResponse);


        return order;
    }

    @Override
    public JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public Page<Order> findAll(int page, int size, OrderFilter orderFilter) {
        return null;
    }
}

