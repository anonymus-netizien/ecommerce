package com.stschool.ecommerce.service;

import com.stschool.ecommerce.dto.ProductDto;
import com.stschool.ecommerce.entity.Product;
import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;
import com.stschool.ecommerce.repository.ProductRepository;
import com.stschool.ecommerce.util.ProductMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final ProductMapper productMapper;

    //Dependency
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    //Overriding Repository Methods
    @Override
    public Product save(Product product) throws ProductExistsException {
        //Checks if Product already exists by id
        productRepository.findByName(product.getName()).ifPresent(p -> {
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
        productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) throws ProductNotFoundException {
        productRepository.delete(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found")));
    }

    @Override
    public List<ProductDto> getAllProductsByAvailability(boolean available) {
        return productRepository.findProductsByIsAvailable(available)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findProductsByCategory(category)
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsByPriceGreaterThan(int price) {
        return productRepository.findProductsByMaxRetailPriceGreaterThan(price)
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<String> getAllProductNames() {
        return productRepository.findAllProductNames();
    }

    @Override
    public long getTotalProductsCount() {
        return productRepository.count();
    }

    @Override
    public boolean hasProductFromCompany(String company) {
        return productRepository.existsByCompanyIgnoreCase(company);
    }

    @Override
    public boolean areAllProductsAvailable() {
        return productRepository.areAllProductsAvailable();
    }

    @Override
    public Optional<Product> findFirstProduct() {
        return productRepository.findFirstByIsAvailableTrue();
    }

    @Override
    public List<String> getDistinctCategories() {
        return productRepository.findDistinctCategories();
    }

    @Override
    public List<ProductDto> getTopNMostExpensiveProducts(int limit) {
        return productRepository.findByOrderByMaxRetailPriceDesc(
                        PageRequest.of(0, limit))
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsSortedByPriceAsc() {
        return productRepository.findByOrderByMaxRetailPriceAsc()
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getProductsSortedByNameDesc() {
        return productRepository.findByOrderByNameDesc()
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public BigDecimal getTotalInventoryValue() {
        return productRepository.calculateTotalInventoryValue();
    }

    @Override
    public BigDecimal calculateFinalPrice(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        if (product.getMaxRetailPrice() < 0) {
            throw new IllegalArgumentException("Product price must not be negative");
        }
        if (product.getDiscountPercentage() < 0 || product.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }

        BigDecimal originalPrice = BigDecimal.valueOf(product.getMaxRetailPrice());
        BigDecimal discount = BigDecimal.valueOf(product.getDiscountPercentage());

        BigDecimal discountAmount = originalPrice.multiply(discount)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return originalPrice.subtract(discountAmount);
    }

    @Override
    public List<ProductDto> getProductsManufacturedAfter(int year) {
        return productRepository.findByManufacturedYearAfter(year)
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductDto> getAvailableProductsWithPriceGreaterThan(double price) {
        return productRepository.findByIsAvailableTrueAndMaxRetailPriceGreaterThan(price)
                .stream().map(productMapper::toDto)
                .toList();
    }

    @Override
    public Map<String, Long> countProductsByCategory() {
        return productRepository.findProductCountByCategory()
                .stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Override
    public Map<String, List<Product>> getProductsGroupedByCategory() {
        return productRepository.findAllProductsByCategory()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    @Override
    public Map<String, List<Product>> getProductsGroupedByCompany() {
        return productRepository.findAllProductsByCompany()
                .stream()
                .collect(Collectors.groupingBy(Product::getCompany));
    }

    @Override
    public Map<Boolean, List<Product>> partitionByAvailability() {
        return productRepository.findAllOrderByAvailability()
                .stream()
                .collect(Collectors.partitioningBy(Product::isAvailable));
    }

    @Override
    public Optional<Product> getMostExpensiveProduct() {
        return productRepository.findTopByOrderByMaxRetailPriceDesc();
    }

    @Override
    public Optional<Product> getLeastExpensiveProduct() {
        return productRepository.findTopByOrderByMaxRetailPriceAsc();
    }

    @Override
    public Map<Integer, Product> getProductMapById(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable)
                .stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()
                ));
    }

    @Override
    public Map<String, BigDecimal> getAveragePriceByCategory() {
        return productRepository.findAveragePriceByCategory()
                .stream().collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> BigDecimal.valueOf((Double) row[1])
                ));
    }

    @Override
    public Map<String, List<Product>> getTopThreeMostExpensiveProductsByCategory() {
        return productRepository.findTopThreeMostExpensiveProductsByCategory()
                .stream().collect(Collectors.groupingBy(Product::getCategory));
    }


}
