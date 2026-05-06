package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final List<Product> products;

    public ProductRepository() {
        this.products = new ArrayList<>();
    }

    //Get all Products
    public List<Product> findAll() {
        return this.products;
    }

    //Get Product by ID
    public Optional<Product> findById(int id) {
        return this.products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    //Save a new Product
    public Product save(Product product) {
        this.products.add(product);
        return product;
    }

    //Update a Product
    public Product update(int id, Product product) {
        this.products.replaceAll(p -> p.getId() == id ? product : p);
        return product;
    }

    //Delete a Product
    public boolean delete(int id) {
        return this.products.removeIf(product -> product.getId() == id);
    }
    /* A Flavour of delete which depends on overriding of equals and hashcode
    public boolean delete(Product product) {
        this.products.remove(product);
        return true;
    }
     */

}
