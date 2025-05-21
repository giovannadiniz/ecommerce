package com.gec.ecommerce.controller;

import com.gec.ecommerce.bases.BaseController;
import com.gec.ecommerce.bases.BasePaginatedResponse;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.dto.ProductShallowDto;
import com.gec.ecommerce.dto.request.ProductRequest;
import com.gec.ecommerce.dto.response.ProductResponse;
import com.gec.ecommerce.filter.ProductFilter;
import com.gec.ecommerce.mapper.ProductMapper;
import com.gec.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
public class ProductController extends BaseController<Product, ProductFilter, ProductShallowDto, ProductRequest, ProductResponse, ProductService> {

    private final ProductService productService;

    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    protected ProductService getEntityService() {
        return this.productService;
    }

    @Override
    public ResponseEntity<BasePaginatedResponse<ProductShallowDto>> listAll(Integer page, Integer size, ProductFilter productFilter, HttpServletRequest request) {
        return ResponseEntity.ok().body(this.productService.listAll(page, size, productFilter, request));
    }

    @Override
    public ResponseEntity<ProductResponse> findEntityById(Long id) {
        return ResponseEntity.ok().body(this.productService.findProductById(id));
    }

    @Override
    public ResponseEntity<ProductResponse> createNew(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.saveWithReturn(productMapper.requestToEntity(productRequest));
        return ResponseEntity.ok().body(productMapper.entityToResponse(product));
    }

    @Override
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductRequest productRequest, Long id) {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    public ResponseEntity<String> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
