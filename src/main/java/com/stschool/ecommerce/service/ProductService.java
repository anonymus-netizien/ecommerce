package com.stschool.ecommerce.service;

import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;
import com.stschool.ecommerce.model.Product;

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
    List<Product> getAllProductsByAvailability(boolean isAvailable);

    //Get all products belonging to a given category
    List<Product> getProductsByCategory(String category);

    //Get all products with price greater than a given value
    List<Product> getProductsByPriceGreaterThan(int price);

    //Get names of all products
    List<String> getAllProductsName();

    //Count how many products are available
    long getTotalProductsCount();

    //Check if there is any product from a given company
    boolean existsProductsByCompany(String company);

    //Check if all products are available
    boolean areAllProductsAvailable();

    //Get the first product safely
    Optional<Product> getFirstProduct();

    //Get all unique categories
    List<String> getDistinctCategories();

    //Get top N most expensive products
    List<Product> getTopNMostExpensiveProducts(int limit);

    //Sort products by price in ascending order
    List<Product> getProductsSortedByPriceAsc();

    //Sort products by name in descending order
    List<Product> getProductsSortedByNameDesc();

    //Get total inventory value (sum of all product prices)
    double calculateTotalInventoryValue();

    //Get total price after applying discounts
    BigDecimal calculateFinalPrice(Product product);

    BigDecimal calculateTotalFinalPrice(List<Product> products);

    //Get all products manufactured after a given year
    List<Product> getProductsManufacturedAfter(int year);

    /*
    Get all products that are:
    - available
    - and price greater than a given value
     */
    List<Product> getAvailableProductsWithPriceGreaterThan(double price);

    //Count number of products in each category
    Map<String, Long> getProductsCountFromCategory();

    //Group all products by category
    Map<String, List<Product>> getProductsGroupedByCategory();

    //Group all products by company
    Map<String, List<Product>> getProductsGroupedByCompany();

    /* Partition products into:
    - available
    - unavailable
     */
    Map<Boolean, List<Product>> getProductsPartitionedByAvailability();

    //Find the most expensive product
    Optional<Product> getProductWithHighestPrice();

    List<Product> getProductsWithHighestPrice();

    //Find the cheapest product
    Optional<Product> getProductWithLowestPrice();

    List<Product> getProductsWithLowestPrice();

    //Create a Map of product ID to Product
    Optional<Product> getProductById(int id);

    Map<Integer, Product> getProductMapById();

    //Find the average price of products per category
    Map<String, BigDecimal> getAveragePriceByCategory();

    //Get top 3 most expensive products in each category
    Map<String, List<Product>> getTopThreeMostExpensiveProductsByCategory();

}
