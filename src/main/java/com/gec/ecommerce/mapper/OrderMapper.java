package com.gec.ecommerce.mapper;


import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Cart;
import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.dto.OrderShallowDto;
import com.gec.ecommerce.dto.request.CartRequest;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.filter.OrderFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class OrderMapper extends BaseMapper<Order, OrderFilter, OrderShallowDto, OrderRequest, OrderResponse> {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "total", ignore = true)
    public abstract Cart requestToEntity(CartRequest request);

    protected Product map(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    // Adicione este método para resolver o problema de mapeamento
    protected Long map(Product product) {
        if (product == null) {
            return null;
        }
        return product.getId();
    }
}
