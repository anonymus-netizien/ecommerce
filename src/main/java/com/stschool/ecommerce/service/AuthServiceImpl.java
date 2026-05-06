package com.stschool.ecommerce.service;

import com.stschool.ecommerce.exception.CustomerExistsException;
import com.stschool.ecommerce.exception.CustomerNotFoundException;
import com.stschool.ecommerce.exception.InvalidCredentialsException;
import com.stschool.ecommerce.model.Customer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerService customerService;

    public AuthServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer signup(Customer customer) throws CustomerExistsException {
        if (customerService.exists(customer.getEmail())) {
            throw new CustomerExistsException("Customer with email " + customer.getEmail() + " already exists");
        }

        return customerService.save(customer);
    }

    @Override
    public Customer login(String email, String password) throws InvalidCredentialsException {
        Customer customer = customerService.getByEmail(email);
        if (customer == null) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        if (!password.equals(customer.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        //Update last login
        customer.setLastLoggedIn(LocalDateTime.now());
        return customer;
    }

    @Override
    public Customer getCustomerByEmail(String email) throws CustomerNotFoundException {
        return customerService.getByEmail(email);
    }
}
