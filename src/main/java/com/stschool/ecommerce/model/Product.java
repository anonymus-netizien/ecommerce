package com.stschool.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    private int id;
    private String name;
    private int maxRetailPrice;
    private float discountPercentage;
    private int rating;
    private boolean isAvailable;
    private String company;
    private String category;
    private int manufacturedYear;
}
