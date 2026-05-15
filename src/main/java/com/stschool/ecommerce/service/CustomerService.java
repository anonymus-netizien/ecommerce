package com.stschool.ecommerce.service;

import com.stschool.ecommerce.entity.Customer;
import com.stschool.ecommerce.exception.CustomerExistsException;
import com.stschool.ecommerce.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    //CREATE
    Customer registerCustomer(Customer customer) throws CustomerExistsException;

    //READ
    List<Customer> getAllCustomers();

    Customer getById(int id) throws CustomerNotFoundException;

    Customer getByEmail(String email) throws CustomerNotFoundException;

    Customer save(Customer customer) throws CustomerExistsException;

    //UPDATE
    Customer update(int id, Customer customer) throws CustomerNotFoundException;

    //DELETE
    void delete(int id);


    boolean exists(String email) throws CustomerNotFoundException;

    Customer login(String email, String password) throws IllegalArgumentException;
}
