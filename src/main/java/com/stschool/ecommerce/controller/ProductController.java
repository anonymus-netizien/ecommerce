package com.stschool.ecommerce.controller;

import com.stschool.ecommerce.exception.ProductExistsException;
import com.stschool.ecommerce.exception.ProductNotFoundException;
import com.stschool.ecommerce.model.Product;
import com.stschool.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
        } catch (ProductExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(productService.getById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            product.setId(id);
            return ResponseEntity.ok(productService.update(id, product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/availability/{isAvailable}")
    public ResponseEntity<?> getAllProductsByAvailability(@PathVariable boolean isAvailable) {
        try {
            return ResponseEntity.ok(productService.getAllProductsByAvailability(isAvailable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        try {
            if (category == null || category.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category must not be blank");
            }
            return ResponseEntity.ok(productService.getProductsByCategory(category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/price-greater-than/{price}")
    public ResponseEntity<?> getProductsByPriceGreaterThan(@PathVariable int price) {
        try {
            if (price < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must not be negative");
            }
            return ResponseEntity.ok(productService.getProductsByPriceGreaterThan(price));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/manufactured-after/{year}")
    public ResponseEntity<?> getProductsManufacturedAfter(@PathVariable int year) {
        try {
            if (year <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Year must be positive");
            }
            return ResponseEntity.ok(productService.getProductsManufacturedAfter(year));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/available-price-greater-than")
    public ResponseEntity<?> getAvailableProductsWithPriceGreaterThan(@RequestParam double price) {
        try {
            if (price < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must not be negative");
            }
            return ResponseEntity.ok(productService.getAvailableProductsWithPriceGreaterThan(price));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/names")
    public ResponseEntity<?> getAllProductsName() {
        try {
            return ResponseEntity.ok(productService.getAllProductsName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getDistinctCategories() {
        try {
            return ResponseEntity.ok(productService.getDistinctCategories());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/count/available")
    public ResponseEntity<?> getTotalProductsCount() {
        try {
            return ResponseEntity.ok(productService.getTotalProductsCount());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/exists/company/{company}")
    public ResponseEntity<?> existsProductsByCompany(@PathVariable String company) {
        try {
            if (company == null || company.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company must not be blank");
            }
            return ResponseEntity.ok(productService.existsProductsByCompany(company));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all-available")
    public ResponseEntity<?> areAllProductsAvailable() {
        try {
            return ResponseEntity.ok(productService.areAllProductsAvailable());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/sort/price-asc")
    public ResponseEntity<?> getProductsSortedByPriceAsc() {
        try {
            return ResponseEntity.ok(productService.getProductsSortedByPriceAsc());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/sort/name-desc")
    public ResponseEntity<?> getProductsSortedByNameDesc() {
        try {
            return ResponseEntity.ok(productService.getProductsSortedByNameDesc());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/top-expensive")
    public ResponseEntity<?> getTopNMostExpensiveProducts(@RequestParam int limit) {
        try {
            if (limit <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Limit must be greater than zero");
            }
            return ResponseEntity.ok(productService.getTopNMostExpensiveProducts(limit));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/top-expensive/by-category")
    public ResponseEntity<?> getTopThreeMostExpensiveProductsByCategory() {
        try {
            return ResponseEntity.ok(productService.getTopThreeMostExpensiveProductsByCategory());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/inventory-value")
    public ResponseEntity<?> calculateTotalInventoryValue() {
        try {
            return ResponseEntity.ok(productService.calculateTotalInventoryValue());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/final-price")
    public ResponseEntity<?> calculateFinalPrice(@RequestBody Product product) {
        try {
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/final-prices")
    public ResponseEntity<?> calculateTotalFinalPrice(@RequestBody List<Product> products) {
        try {
            if (products == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Products must not be null");
            }
            for (Product product : products) {
                if (product == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product list must not contain null values");
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
            }
            return ResponseEntity.ok(productService.calculateTotalFinalPrice(products));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/average-price/by-category")
    public ResponseEntity<?> getAveragePriceByCategory() {
        try {
            return ResponseEntity.ok(productService.getAveragePriceByCategory());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/count/by-category")
    public ResponseEntity<?> getProductsCountFromCategory() {
        try {
            return ResponseEntity.ok(productService.getProductsCountFromCategory());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/grouped/by-category")
    public ResponseEntity<?> getProductsGroupedByCategory() {
        try {
            return ResponseEntity.ok(productService.getProductsGroupedByCategory());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/grouped/by-company")
    public ResponseEntity<?> getProductsGroupedByCompany() {
        try {
            return ResponseEntity.ok(productService.getProductsGroupedByCompany());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/partitioned/by-availability")
    public ResponseEntity<?> getProductsPartitionedByAvailability() {
        try {
            return ResponseEntity.ok(productService.getProductsPartitionedByAvailability());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/highest-price")
    public ResponseEntity<?> getProductWithHighestPrice() {
        try {
            Optional<Product> product = productService.getProductWithHighestPrice();
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/highest-price/all")
    public ResponseEntity<?> getProductsWithHighestPrice() {
        try {
            return ResponseEntity.ok(productService.getProductsWithHighestPrice());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/lowest-price")
    public ResponseEntity<?> getProductWithLowestPrice() {
        try {
            Optional<Product> product = productService.getProductWithLowestPrice();
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/lowest-price/all")
    public ResponseEntity<?> getProductsWithLowestPrice() {
        try {
            return ResponseEntity.ok(productService.getProductsWithLowestPrice());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/first")
    public ResponseEntity<?> getFirstProduct() {
        try {
            Optional<Product> product = productService.getFirstProduct();
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
            }
            return ResponseEntity.ok(product.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
        try {
            return ResponseEntity.ok(productService.getProductMapById());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
