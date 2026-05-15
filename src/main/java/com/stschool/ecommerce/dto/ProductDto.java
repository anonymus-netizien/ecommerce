package com.stschool.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDto {
    private String name;
    private int maxRetailPrice;
    private float discountPercentage;
    private int rating;
    private boolean isAvailable;
}
