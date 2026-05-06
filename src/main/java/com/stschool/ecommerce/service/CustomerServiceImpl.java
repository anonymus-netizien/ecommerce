package com.stschool.ecommerce.service;

import com.stschool.ecommerce.enums.Membership;
import com.stschool.ecommerce.enums.Status;
import com.stschool.ecommerce.exception.CustomerExistsException;
import com.stschool.ecommerce.exception.CustomerNotFoundException;
import com.stschool.ecommerce.model.Customer;
import com.stschool.ecommerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // CREATE
    @Override
    public Customer registerCustomer(Customer customer) throws CustomerExistsException {
        if (customerRepository.exists(customer.getEmail())) {
            throw new CustomerExistsException("Customer already exists with email: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    // READ ALL
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // READ BY ID
    @Override
    public Customer getById(int id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id " + id + " not found"));
    }

    // READ BY EMAIL
    @Override
    public Customer getByEmail(String email) throws CustomerNotFoundException {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + email + " not found"));
    }

    @Override
    public Customer save(Customer customer) throws CustomerExistsException {
        // 1. Check if email already exists
        if (customerRepository.exists(customer.getEmail())) {
            throw new CustomerExistsException(
                    "Customer already exists with email: " + customer.getEmail());
        }

        // 2. Set default values (good practice 🔥)
        customer.setCreatedOn(LocalDateTime.now());
        customer.setLastLoggedIn(null);

        // Optional defaults (if not set)
        if (customer.getStatus() == null) {
            customer.setStatus(Status.ACTIVE);
        }

        if (customer.getMembership() == null) {
            customer.setMembership(Membership.BASIC);
        }

        //save customer
        return customerRepository.save(customer);
    }

    // UPDATE
    @Override
    public Customer update(int id, Customer customer) throws CustomerNotFoundException {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + id));

        // Optional: prevent duplicate email
        if (!existing.getEmail().equalsIgnoreCase(customer.getEmail()) &&
                customerRepository.exists(customer.getEmail())) {
            throw new RuntimeException("Email already in use " + customer.getEmail());
        }
        return customerRepository.update(customer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with this id " + id));
    }

    // DELETE
    @Override
    public void delete(int id) {
        boolean deleted = customerRepository.delete(id);

        if (!deleted) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
    }

    @Override
    public boolean exists(String email) throws CustomerNotFoundException {
        return this.customerRepository.exists(email);
    }

    @Override
    public Customer login(String email, String password) throws IllegalArgumentException {
        //Normalize email
        email = email.trim().toLowerCase();

        try {
            String finalEmail = email;
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email " + finalEmail));

            // validate password
            if (!customer.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid password");
            }

            // Check account status (optional but recommended)
            if (customer.getStatus() != null && customer.getStatus() != Status.ACTIVE) {
                throw new IllegalArgumentException("Account is not Active");
            }

            // update last login
            customer.setLastLoggedIn(LocalDateTime.now());
            customerRepository.update(customer);
            return customer;

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
