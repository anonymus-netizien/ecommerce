package com.stschool.ecommerce.entity;

import com.stschool.ecommerce.enums.Gender;
import com.stschool.ecommerce.enums.Membership;
import com.stschool.ecommerce.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String password;
    private byte age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Membership membership;
    private LocalDateTime createdOn;
    private LocalDateTime lastLoggedIn;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNo", column = @Column(name = "residential_house_no")),
            @AttributeOverride(name = "street", column = @Column(name = "residential_street")),
            @AttributeOverride(name = "area", column = @Column(name = "residential_area")),
            @AttributeOverride(name = "city", column = @Column(name = "residential_city")),
            @AttributeOverride(name = "pincode", column = @Column(name = "residential_pincode"))
    })
    private Address residentialAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNo", column = @Column(name = "shipping_house_no")),
            @AttributeOverride(name = "street", column = @Column(name = "shipping_street")),
            @AttributeOverride(name = "area", column = @Column(name = "shipping_area")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "pincode", column = @Column(name = "shipping_pincode"))
    })
    private Address shippingAddress;
}
