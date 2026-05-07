package com.stschool.ecommerce.controller;

import com.stschool.ecommerce.model.Product;
import com.stschool.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/availability/{isAvailable}")
    public ResponseEntity<?> getAllProductsByAvailability(@PathVariable boolean isAvailable) {
        return ResponseEntity.ok(productService.getAllProductsByAvailability(isAvailable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        if (category == null || category.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category must not be blank");
        }
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @GetMapping("/price-greater-than/{price}")
    public ResponseEntity<?> getProductsByPriceGreaterThan(@PathVariable int price) {
        if (price < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must not be negative");
        }
        return ResponseEntity.ok(productService.getProductsByPriceGreaterThan(price));
    }

    @GetMapping("/manufactured-after/{year}")
    public ResponseEntity<?> getProductsManufacturedAfter(@PathVariable int year) {
        if (year <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Year must be positive");
        }
        return ResponseEntity.ok(productService.getProductsManufacturedAfter(year));
    }

    @GetMapping("/available-price-greater-than")
    public ResponseEntity<?> getAvailableProductsWithPriceGreaterThan(@RequestParam double price) {
        if (price < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must not be negative");
        }
        return ResponseEntity.ok(productService.getAvailableProductsWithPriceGreaterThan(price));
    }

    @GetMapping("/names")
    public ResponseEntity<?> getAllProductNames() {
        return ResponseEntity.ok(productService.getAllProductNames());
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getDistinctCategories() {
        return ResponseEntity.ok(productService.getDistinctCategories());
    }

    @GetMapping("/count/available")
    public ResponseEntity<?> getTotalProductsCount() {
        return ResponseEntity.ok(productService.getTotalProductsCount());
    }

    @GetMapping("/exists/company/{company}")
    public ResponseEntity<?> hasProductFromCompany(@PathVariable String company) {
        if (company == null || company.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company must not be blank");
        }
        return ResponseEntity.ok(productService.hasProductFromCompany(company));
    }

    @GetMapping("/all-available")
    public ResponseEntity<?> areAllProductsAvailable() {
        return ResponseEntity.ok(productService.areAllProductsAvailable());
    }

    @GetMapping("/sort/price-asc")
    public ResponseEntity<?> getProductsSortedByPriceAsc() {
        return ResponseEntity.ok(productService.getProductsSortedByPriceAsc());
    }

    @GetMapping("/sort/name-desc")
    public ResponseEntity<?> getProductsSortedByNameDesc() {
        return ResponseEntity.ok(productService.getProductsSortedByNameDesc());
    }

    @GetMapping("/top-expensive")
    public ResponseEntity<?> getTopNMostExpensiveProducts(@RequestParam int limit) {
        if (limit <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Limit must be greater than zero");
        }
        return ResponseEntity.ok(productService.getTopNMostExpensiveProducts(limit));
    }

    @GetMapping("/top-expensive/by-category")
    public ResponseEntity<?> getTopThreeMostExpensiveProductsByCategory() {
        return ResponseEntity.ok(productService.getTopThreeMostExpensiveProductsByCategory());
    }

    @GetMapping("/inventory-value")
    public ResponseEntity<?> calculateTotalInventoryValue() {
        return ResponseEntity.ok(productService.calculateTotalInventoryValue());
    }

    @PostMapping("/final-price")
    public ResponseEntity<?> calculateFinalPrice(@RequestBody Product product) {
        if (product == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product must not be null");
        }
        if (product.getMaxRetailPrice() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product price must not be negative");
        }
        if (product.getDiscountPercentage() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount percentage must not be negative");
        }
        if (product.getDiscountPercentage() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount percentage must not be greater than 100");
        }
        return ResponseEntity.ok(productService.calculateFinalPrice(product));
    }

    @GetMapping("/average-price/by-category")
    public ResponseEntity<?> getAveragePriceByCategory() {
        return ResponseEntity.ok(productService.getAveragePriceByCategory());
    }

    @GetMapping("/count/by-category")
    public ResponseEntity<?> countProductsByCategory() {
        return ResponseEntity.ok(productService.countProductsByCategory());
    }

    @GetMapping("/grouped/by-category")
    public ResponseEntity<?> getProductsGroupedByCategory() {
        return ResponseEntity.ok(productService.groupProductsByCategory());
    }

    @GetMapping("/grouped/by-company")
    public ResponseEntity<?> getProductsGroupedByCompany() {
        return ResponseEntity.ok(productService.groupProductsByCompany());
    }

    @GetMapping("/partitioned/by-availability")
    public ResponseEntity<?> partitionByAvailability() {
        return ResponseEntity.ok(productService.partitionByAvailability());
    }

    @GetMapping("/highest-price")
    public ResponseEntity<?> getMaxPricedProduct() {
        return ResponseEntity.ok(productService.getMaxPricedProduct());
    }

    @GetMapping("/lowest-price")
    public ResponseEntity<?> getMinPricedProduct() {
        return ResponseEntity.ok(productService.getMinPricedProduct());
    }

    @GetMapping("/first")
    public ResponseEntity<?> findFirstProduct() {
        return ResponseEntity.ok(productService.findFirstProduct());
    }

    @GetMapping("/optional/{id}")
    public ResponseEntity<?> getProductByIdOptional(@PathVariable int id) {
        try {
            Optional<Product> product = productService.getProductById(id);
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id " + id + " not found");
            }
            return ResponseEntity.ok(product.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/map/by-id")
    public ResponseEntity<?> getProductMapById() {
        return ResponseEntity.ok(productService.getProductMapById());
    }

}
