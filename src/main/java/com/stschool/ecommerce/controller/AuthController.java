package com.stschool.ecommerce.controller;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(customer));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials loginCredentials) {
        return ResponseEntity.ok(authService.login(loginCredentials.getEmail(), loginCredentials.getPassword()));
    }

    @GetMapping("/customers/by-email")
    public Customer getCustomerByEmail(@RequestParam String email) {
        return authService.getCustomerByEmail(email);
    }
}
