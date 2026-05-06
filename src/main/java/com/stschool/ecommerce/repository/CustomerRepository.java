package com.stschool.ecommerce.repository;

import com.stschool.ecommerce.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private final List<Customer> customers;

    public CustomerRepository() {
        this.customers = new ArrayList<>();
    }

    //Get all Customers
    public List<Customer> findAll() {
        return this.customers;
    }

    //Get Customer by ID
    public Optional<Customer> findById(int id) {
        return this.customers.stream()
                .filter(customer -> customer.getId() == id)
                .findFirst();
    }

    //Save a new Customer
    public Customer save(Customer customer) {
        this.customers.add(customer);
        return customer;
    }

    //Update a Customer
    public Optional<Customer> update(Customer updatedCustomer) {
        return findById(updatedCustomer.getId())
                .map(existing -> {
                    Customer updated = Customer.builder()
                            .id(updatedCustomer.getId())
                            .name(updatedCustomer.getName())
                            .email(updatedCustomer.getEmail())
                            .phoneNo(updatedCustomer.getPhoneNo())
                            .password(updatedCustomer.getPassword())
                            .age(updatedCustomer.getAge())
                            .gender(updatedCustomer.getGender())
                            .status(updatedCustomer.getStatus())
                            .membership(updatedCustomer.getMembership())
                            .residentialAddress(updatedCustomer.getResidentialAddress())
                            .shippingAddress(updatedCustomer.getShippingAddress())
                            .createdOn(existing.getCreatedOn()) //preserve createdOn
                            .lastLoggedIn(updatedCustomer.getLastLoggedIn())
                            .build();

                    this.customers.replaceAll(customer -> customer.getId() == updated.getId() ? updated : customer);
                    return updated;
                });
    }

    //Delete a Customer
    public boolean delete(int id) {
        return this.customers.removeIf(customer -> customer.getId() == id);
    }

    // FIND BY EMAIL
    public Optional<Customer> findByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // EXISTS BY EMAIL
    public boolean exists(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }

}
