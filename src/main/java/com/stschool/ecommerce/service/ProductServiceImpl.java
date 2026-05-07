package com.stschool.ecommerce.service;

import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;
import com.stschool.ecommerce.model.Product;
import com.stschool.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    //Dependency
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Overriding Repository Methods
    @Override
    public Product save(Product product) throws ProductExistsException {
        //Checks if Product already exists by id
        productRepository.findById(product.getId()).ifPresent(p -> {
            throw new ProductExistsException("Product with id " + product.getId() + " already exists");
        });
        return productRepository.save(product);

    }

    @Override
    public Product getById(int id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(int id, Product product) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return productRepository.update(id, product);
    }

    @Override
    public void delete(int id) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        productRepository.delete(id);
    }

    @Override
    public List<Product> getAllProductsByAvailability(boolean available) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.isAvailable() == available)
                .toList();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .toList();
    }

    @Override
    public List<Product> getProductsByPriceGreaterThan(int price) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getMaxRetailPrice() > price)
                .toList();
    }

    @Override
    public List<String> getAllProductNames() {
        return productRepository.findAll()
                .stream()
                .map(Product::getName)
                .toList();
    }

    @Override
    public long getTotalProductsCount() {
        return productRepository.findAll()
                .stream()
                .filter(Product::isAvailable)
                .count();
    }

    @Override
    public boolean hasProductFromCompany(String company) {
        return productRepository.findAll()
                .stream()
                .anyMatch(product -> product.getCompany().equals(company));
    }

    @Override
    public boolean areAllProductsAvailable() {
        return productRepository.findAll()
                .stream()
                .allMatch(Product::isAvailable);
    }

    @Override
    public Optional<Product> findFirstProduct() {
        return productRepository.findAll()
                .stream()
                .findFirst();
    }

    @Override
    public List<String> getDistinctCategories() {
        return productRepository.findAll()
                .stream()
                .map(Product::getCategory)
                .map(String::toLowerCase)
                .distinct()
                .toList();
    }

    @Override
    public List<Product> getTopNMostExpensiveProducts(int limit) {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Product::getMaxRetailPrice).reversed())
                .limit(limit)
                .toList();
    }

    @Override
    public List<Product> getProductsSortedByPriceAsc() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Product::getMaxRetailPrice))
                .toList();
    }

    @Override
    public List<Product> getProductsSortedByNameDesc() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(p -> p.getName().toLowerCase(), Comparator.reverseOrder()))
                .toList();
    }

    @Override
    public double calculateTotalInventoryValue() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(Product::getMaxRetailPrice).sum();
    }

    @Override
    public BigDecimal calculateFinalPrice(Product product) {
        BigDecimal originalPrice = BigDecimal.valueOf(product.getMaxRetailPrice());
        BigDecimal discountPercentage = BigDecimal.valueOf(product.getDiscountPercentage());

        BigDecimal discountAmount = originalPrice.multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return originalPrice.subtract(discountAmount);
    }

    @Override
    public List<Product> getProductsManufacturedAfter(int year) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getManufacturedYear() > year)
                .toList();
    }

    @Override
    public List<Product> getAvailableProductsWithPriceGreaterThan(double price) {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getMaxRetailPrice() > price)
                .toList();
    }

    @Override
    public Map<String, Long> countProductsByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
    }

    @Override
    public Map<String, List<Product>> groupProductsByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.toList()));
    }

    @Override
    public Map<String, List<Product>> groupProductsByCompany() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCompany, Collectors.toList()));
    }

    @Override
    public Map<Boolean, List<Product>> partitionByAvailability() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.partitioningBy(Product::isAvailable));
    }

    @Override
    public Product getMaxPricedProduct() {
        return productRepository.findAll().stream()
                .max(Comparator.comparing(Product::getMaxRetailPrice))
                .orElseThrow(() -> new ProductNotFoundException("No products available"));
    }

    @Override
    public Product getMinPricedProduct() {
        return productRepository.findAll().stream()
                .min(Comparator.comparingInt(Product::getMaxRetailPrice))
                .orElseThrow(() -> new ProductNotFoundException("No products available"));
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public Map<Integer, Product> getProductMapById() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    @Override
    public Map<String, BigDecimal> getAveragePriceByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.collectingAndThen(
                                Collectors.averagingInt(Product::getMaxRetailPrice),
                                //BigDecimal::valueOf
                                avg -> BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP)
                        )
                ));
    }

    @Override
    public Map<String, List<Product>> getTopThreeMostExpensiveProductsByCategory() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.collectingAndThen(
                        Collectors.toList(), list -> list.stream()
                                .sorted(Comparator.comparingInt(Product::getMaxRetailPrice).reversed())
                                .limit(3)
                                .toList()
                )));
    }


}
