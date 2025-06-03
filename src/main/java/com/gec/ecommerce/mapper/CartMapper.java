package com.gec.ecommerce.mapper;

import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.dto.CartShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.response.CartResponse;
import com.gec.ecommerce.filter.CartFilter;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CartMapper extends BaseMapper<Cart, CartFilter,CartShallowDto,CartRequest, CartResponse>
{
}
