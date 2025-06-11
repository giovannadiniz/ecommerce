package com.gec.ecommerce.service;

import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.bases.BaseService;
import com.gec.ecommerce.domain.Coupon;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.dto.CouponShallowDto;
import com.gec.ecommerce.dto.ProductShallowDto;
import com.gec.ecommerce.dto.request.ProductRequest;
import com.gec.ecommerce.dto.response.ProductResponse;
import com.gec.ecommerce.exception.CouponNotFoundException;
import com.gec.ecommerce.filter.CouponFilter;
import com.gec.ecommerce.filter.ProductFilter;
import com.gec.ecommerce.mapper.ProductMapper;
import com.gec.ecommerce.repository.ProductRepository;
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
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, ProductFilter> {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    @Transactional
    @Override
    public Product saveWithReturn(Product product) {
        return getRepository().save(product);
    }

    public List<ProductResponse> pesquisarProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getQuantity(),
                        product.isActive()
                ))
                .collect(Collectors.toList());
    }

    public BasePaginatedResponse<ProductShallowDto> listAll(Integer page, Integer size, ProductFilter productFilter, HttpServletRequest request){
        UriComponentsBuilder uri = UriComponentsBuilder.fromPath(request.getServletPath()).query(request.getQueryString());

        Page<Product> productPage = findAll(page,size,productFilter);

        return new BasePaginatedResponse<>(productPage.map(productMapper::entityToShallowDto),uri);
    }

    @Override
    public Page<Product> findAll(int page, int size, ProductFilter productFilter) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Specification<Product> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productFilter.name() != null)
                predicates.add(cb.equal(root.get("name"), productFilter.name()));
            if (productFilter.price() != null)
                predicates.add(cb.equal(root.get("createdAt"), productFilter.price()));
            if (productFilter.active() != null)
                predicates.add(cb.equal(root.get("amount"), productFilter.active()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return productRepository.findAll(spec, pageable);
    }

    public ProductResponse findProductById(Long id) {
        Product product = findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.entityToResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        var productUpdated = productMapper.requestToEntity(request);

        BeanUtils.copyProperties(productUpdated, existingProduct, "id");

        var updatedProduct = productRepository.save(existingProduct);

        return productMapper.entityToResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> product = findById(id);
        product.ifPresentOrElse(
                productRepository::delete,
                () -> {
                    throw new RuntimeException("Product not found");
                }
        );
    }



}
