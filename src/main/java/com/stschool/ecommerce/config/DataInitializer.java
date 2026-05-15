package com.stschool.ecommerce.config;

import com.stschool.ecommerce.entity.Product;
import com.stschool.ecommerce.repository.ProductRepository;
import com.stschool.ecommerce.util.CsvParser;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CsvParser csvParser;

    public DataInitializer(ProductRepository productRepository,
                           CsvParser csvParser) {
        this.productRepository = productRepository;
        this.csvParser = csvParser;
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        if (productRepository.count() == 0) {
            // Load CSV
            List<Product> products = csvParser.getProductsFromCsv();
            // Save to DB
            productRepository.saveAll(products);
        }
    }
}
