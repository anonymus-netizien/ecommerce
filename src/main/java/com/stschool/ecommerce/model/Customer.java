package com.stschool.ecommerce.model;

import com.stschool.ecommerce.enums.Gender;
import com.stschool.ecommerce.enums.Membership;
import com.stschool.ecommerce.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private byte age;
    private Gender gender;
    private Status status;
    private Membership membership;
    private LocalDateTime createdOn;
    private LocalDateTime lastLoggedIn;
    private Address residentialAddress;
    private Address shippingAddress;
}
