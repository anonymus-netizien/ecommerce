package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    List<Product> findProductsByIsAvailable(boolean isAvailable);

    List<Product> findProductsByCategory(String category);

    List<Product> findProductsByMaxRetailPriceGreaterThan(int maxRetailPrice);

    @Query("SELECT name FROM Product")
    List<String> findAllProductNames();

    boolean existsByCompanyIgnoreCase(String company);

    @Query("SELECT count(p) = 0 FROM Product p WHERE p.isAvailable = false")
    boolean areAllProductsAvailable();

    Optional<Product> findFirstByIsAvailableTrue();

    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findDistinctCategories();

    //@Query(value = "SELECT * FROM products ORDER BY max_retail_price DESC LIMIT :limit",nativeQuery = true)
    @Query("FROM Product p ORDER BY p.maxRetailPrice DESC")
    List<Product> findByOrderByMaxRetailPriceDesc(PageRequest limit);

    List<Product> findByOrderByMaxRetailPriceAsc();

    List<Product> findByOrderByNameDesc();

    @Query("SELECT COALESCE(SUM(p.maxRetailPrice)) FROM Product p")
    BigDecimal calculateTotalInventoryValue();

    List<Product> findByManufacturedYearAfter(int year);

    List<Product> findByIsAvailableTrueAndMaxRetailPriceGreaterThan(double price);

    @Query("SELECT p.category, COUNT(p) FROM Product p GROUP BY p.category")
    List<Object[]> findProductCountByCategory();

    @Query("SELECT p FROM Product p GROUP BY p.category")
    List<Product> findAllProductsByCategory();

    @Query("SELECT p FROM Product p GROUP BY p.company")
    List<Product> findAllProductsByCompany();

    @Query("SELECT p FROM Product p ORDER BY p.isAvailable")
    List<Product> findAllOrderByAvailability();

    Optional<Product> findTopByOrderByMaxRetailPriceDesc();

    Optional<Product> findTopByOrderByMaxRetailPriceAsc();

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p.category, AVG(p.maxRetailPrice) FROM Product p GROUP BY p.category")
    List<Object[]> findAveragePriceByCategory();

    @Query(value = """
            SELECT * FROM (
                SELECT p.*,
                       ROW_NUMBER() OVER (
                           PARTITION BY category
                           ORDER BY max_retail_price DESC
                       ) AS rn
                FROM products p
            ) ranked
            WHERE ranked.rn <= 3
            """, nativeQuery = true)
    List<Product> findTopThreeMostExpensiveProductsByCategory();
}
