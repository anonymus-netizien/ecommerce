package com.stschool.ecommerce.controller;

import com.stschool.ecommerce.exception.CustomerExistsException;
import com.stschool.ecommerce.exception.CustomerNotFoundException;
import com.stschool.ecommerce.model.Customer;
import com.stschool.ecommerce.model.LoginCredentials;
import com.stschool.ecommerce.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Customer customer) {
        try {
            Customer signedUpCustomer = authService.signup(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(signedUpCustomer);
        } catch (CustomerExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials) {
        try {
            return ResponseEntity.ok(authService.login(loginCredentials.getEmail(), loginCredentials.getPassword()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/customers/by-email")
    public Customer getCustomerByEmail(@RequestParam String email) throws CustomerNotFoundException {
        return authService.getCustomerByEmail(email);
    }
}
