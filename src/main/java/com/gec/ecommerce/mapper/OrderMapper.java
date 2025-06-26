package com.gec.ecommerce.mapper;


import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.Order;
import com.gec.ecommerce.domain.Product;
import com.gec.ecommerce.dto.OrderShallowDto;
import com.gec.ecommerce.dto.request.OrderRequest;
import com.gec.ecommerce.dto.response.OrderResponse;
import com.gec.ecommerce.filter.OrderFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public abstract class OrderMapper extends BaseMapper<Order, OrderFilter, OrderShallowDto, OrderRequest, OrderResponse> {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product", target = "productId")
    @Override
    public abstract OrderResponse entityToResponse(Order entity);

    public Product mapProduct(Product product) {
        if (product == null) return null;

        Product productResponse = new Product();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setQuantity(product.getQuantity());
        return productResponse;
    }

    public static Product mapIdToProduct(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "qrCodeImage", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Override
    public abstract Order requestToEntity(OrderRequest request);

}
