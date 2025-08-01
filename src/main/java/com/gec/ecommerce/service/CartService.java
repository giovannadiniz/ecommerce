package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import com.gec.ecommerce.mapper.CartMapper;
import com.gec.ecommerce.repository.CartRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
    private final UserService userService;
    private final ProductService productService;

    public CartService(CartRepository cartRepository,
                       CartMapper cartMapper,
                       UserService userService,
                       ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public JpaRepository<Cart, Long> getRepository() {
        return cartRepository;
    }

    @Override
    public Page<Cart> findAll(int page, int size, CartFilter cartFilter) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Specification<Cart> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (cartFilter.id() != null)
                predicates.add(cb.equal(root.get("id"), cartFilter.id()));
            if (cartFilter.userId() != null)
                predicates.add(cb.equal(root.get("user").get("id"), cartFilter.userId()));
            if (cartFilter.productId() != null)
                predicates.add(cb.equal(root.get("product").get("id"), cartFilter.productId()));
            if (cartFilter.total() != null)
                predicates.add(cb.equal(root.get("total"), cartFilter.total()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return cartRepository.findAll(spec, pageable);
    }

    @Transactional
    public Cart createOrUpdateCart(CartRequest request) {
        User user = userService.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productService.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<Cart> existingCart = cartRepository.findByUserId(request.userId());

        Cart cart;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
            cart.setProductName(product.getName());
            cart.setProduct(product);
            cart.setQuantity(request.quantity() != null ? request.quantity() : 1);
            logger.info("Updating existing cart for user: " + request.userId());
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setProductName(product.getName());
            cart.setQuantity(request.quantity() != null ? request.quantity() : 1);
            logger.info("Creating new cart for user: " + request.userId());
        }

        cart.calculateTotal();
        return saveWithReturn(cart);
    }

    @Transactional
    public Cart createCart(CartRequest request) {
        return createOrUpdateCart(request);
    }

    public BasePaginatedResponse<CartShallowDto> listAll(Integer page, Integer size, CartFilter cartFilter, HttpServletRequest request) {
        logger.info("Listing carts - page: " + page + ", size: " + size);
        UriComponentsBuilder uri = UriComponentsBuilder.fromPath(request.getServletPath()).query(request.getQueryString());
        Page<Cart> cartPage = findAll(page, size, cartFilter);
        return new BasePaginatedResponse<>(cartPage.map(cartMapper::entityToShallowDto), uri);
    }

    public CartResponse findCartById(Long id) {
        logger.info("Finding cart by ID: " + id);
        Cart cart = findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
        return cartMapper.entityToResponse(cart);
    }

    public CartResponse findCartByUserId(Long userId) {
        logger.info("Finding cart by user ID: " + userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));
        return cartMapper.entityToResponse(cart);
    }

    public boolean hasCart(Long userId) {
        logger.info("Checking if user has cart: " + userId);
        return cartRepository.findByUserId(userId).isPresent();
    }

    public Optional<CartResponse> findCartByUserIdOptional(Long userId) {
        logger.info("Finding cart by user ID (optional): " + userId);
        return cartRepository.findByUserId(userId)
                .map(cartMapper::entityToResponse);
    }

    @Transactional
    public CartResponse updateCart(Long id, CartRequest request) {

        Cart existingCart = findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found for ID: " + id));

        Product product = productService.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + request.productId()));

        existingCart.setProduct(product);
        existingCart.setProductName(product.getName());
        existingCart.setQuantity(request.quantity());
        existingCart.calculateTotal();

        Cart updatedCart = saveWithReturn(existingCart);
        return cartMapper.entityToResponse(updatedCart);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        logger.info("Deleting cart by user ID: " + userId);
        cartRepository.findByUserId(userId)
                .ifPresent(cart -> super.delete(cart.getId())); 
    }

    @Transactional
    public void clearCart(Long userId) {
        logger.info("Clearing cart for user: " + userId);
        cartRepository.findByUserId(userId)
                .ifPresent(cart -> {
                    cart.setProduct(null);
                    cart.setQuantity(0);
                    cart.setTotal(java.math.BigDecimal.ZERO);
                    saveWithReturn(cart);
                });
    }
}