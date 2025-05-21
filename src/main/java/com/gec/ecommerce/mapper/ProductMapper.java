package com.gec.ecommerce.mapper;

import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.dto.ProductShallowDto;
import com.gec.ecommerce.dto.request.ProductRequest;
import com.gec.ecommerce.dto.response.ProductResponse;
import com.gec.ecommerce.filter.ProductFilter;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class ProductMapper extends BaseMapper<Product, ProductFilter, ProductShallowDto, ProductRequest, ProductResponse>
    {}
