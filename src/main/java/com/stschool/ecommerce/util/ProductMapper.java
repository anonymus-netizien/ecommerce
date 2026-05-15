package com.stschool.ecommerce.util;

import com.stschool.ecommerce.dto.ProductDto;
import com.stschool.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .maxRetailPrice(product.getMaxRetailPrice())
                .discountPercentage(product.getDiscountPercentage())
                .rating(product.getRating())
                .isAvailable(product.isAvailable())
                .build();
    }
}
