package com.gec.ecommerce.mapper;

import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CartMapper extends BaseMapper<Cart, CartFilter, CartShallowDto, CartRequest, CartResponse> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.price", target = "price")
    @Mapping(source = "product.name", target = "productName")
    @Override
    public abstract CartResponse entityToResponse(Cart entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Override
    public abstract Cart requestToEntity(CartRequest request);
}