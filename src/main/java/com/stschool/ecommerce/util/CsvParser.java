package com.stschool.ecommerce.util;

import com.stschool.ecommerce.model.Product;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    private static final String PRODUCTS_CSV_PATH = "products.csv";
    private static final String PRODUCTS_HEADER = "name,maxRetailPrice,discountPercentage,rating,isAvailable,company,category,manufacturedYear";
    private static final int PRODUCT_FIELD_COUNT = 8;

    public List<Product> getProductsFromCsv() throws IOException {
        ClassPathResource resource = new ClassPathResource(PRODUCTS_CSV_PATH);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            validateHeader(reader.readLine());

            List<Product> products = new ArrayList<>();
            String row;
            int rowNumber = 1;

            while ((row = reader.readLine()) != null) {
                rowNumber++;

                if (row.isBlank()) {
                    continue;
                }

                products.add(parseProduct(row, rowNumber));
            }
            return products;
        }
    }

    private void validateHeader(String header) {
        if (!PRODUCTS_HEADER.equals(header)) {
            throw new IllegalArgumentException("Invalid products CSV header. Expected: " + PRODUCTS_HEADER);
        }
    }

    private Product parseProduct(String row, int rowNumber) {
        String[] fields = row.split(",", -1);

        if (fields.length != PRODUCT_FIELD_COUNT) {
            throw new IllegalArgumentException("Invalid product CSV row at line " + rowNumber + ": " + row);
        }

        return Product.builder()
                .name(fields[0].trim())
                .maxRetailPrice(parseInt(fields[1], "maxRetailPrice", rowNumber))
                .discountPercentage(parseFloat(fields[2], "discountPercentage", rowNumber))
                .rating(parseInt(fields[3], "rating", rowNumber))
                .isAvailable(Boolean.parseBoolean(fields[4].trim()))
                .company(fields[5].trim())
                .category(fields[6].trim())
                .manufacturedYear(parseInt(fields[7], "manufacturedYear", rowNumber))
                .build();
    }

    private int parseInt(String value, String fieldName, int rowNumber) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid integer for " + fieldName + " at CSV line " + rowNumber + ": " + value, exception);
        }
    }

    private float parseFloat(String value, String fieldName, int rowNumber) {
        try {
            return Float.parseFloat(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid decimal for " + fieldName + " at CSV line " + rowNumber + ": " + value, exception);
        }
    }
}
