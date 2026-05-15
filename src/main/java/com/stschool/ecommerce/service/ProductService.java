package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.ProductDto;
import com.stschool.ecommerce.entity.Product;
import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    Product save(Product product) throws ProductExistsException;

    Product getById(int id) throws ProductNotFoundException;

    List<Product> getAll();

    Product update(int id, Product product) throws ProductNotFoundException;

    void delete(int id) throws ProductNotFoundException;

    //Get all available products based on availability
    List<ProductDto> getAllProductsByAvailability(boolean isAvailable);

    //Get all products belonging to a given category
    List<ProductDto> getProductsByCategory(String category);

    //Get all products with price greater than a given value
    List<ProductDto> getProductsByPriceGreaterThan(int price);

    //Get names of all products
    List<String> getAllProductNames();

    //Count how many products are available
    long getTotalProductsCount();

    //Check if there is any product from a given company
    boolean hasProductFromCompany(String company);

    //Check if all products are available
    boolean areAllProductsAvailable();

    //Get the first product safely
    Optional<Product> findFirstProduct();

    //Get all unique categories
    List<String> getDistinctCategories();

    //Get top N most expensive products
    List<ProductDto> getTopNMostExpensiveProducts(int limit);

    //Sort products by price in ascending order
    List<ProductDto> getProductsSortedByPriceAsc();

    //Sort products by name in descending order
    List<ProductDto> getProductsSortedByNameDesc();

    //Get total inventory value (sum of all product prices)
    BigDecimal getTotalInventoryValue();

    //Get total price after applying discounts
    BigDecimal calculateFinalPrice(int id);

    //Get all products manufactured after a given year
    List<ProductDto> getProductsManufacturedAfter(int year);

    /*
    Get all products that are:
    - available
    - and price greater than a given value
     */
    List<ProductDto> getAvailableProductsWithPriceGreaterThan(double price);

    //Count number of products in each category
    Map<String, Long> countProductsByCategory();

    //Group all products by category
    Map<String, List<Product>> getProductsGroupedByCategory();

    //Group all products by company
    Map<String, List<Product>> getProductsGroupedByCompany();

    /* Partition products into:
    - available
    - unavailable
     */
    Map<Boolean, List<Product>> partitionByAvailability();

    //Find the most expensive product
    Optional<Product> getMostExpensiveProduct();

    //Find the cheapest product
    Optional<Product> getLeastExpensiveProduct();

    //Create a Map of product ID to Product
    Map<Integer, Product> getProductMapById(int page, int size);

    //Find the average price of products per category
    Map<String, BigDecimal> getAveragePriceByCategory();

    //Get top 3 most expensive products in each category
    Map<String, List<Product>> getTopThreeMostExpensiveProductsByCategory();

}
