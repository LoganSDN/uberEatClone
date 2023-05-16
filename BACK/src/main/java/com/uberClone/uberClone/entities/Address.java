package com.uberClone.uberClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String ZIP;
    @OneToOne(mappedBy = "address")
    private Restaurants restaurant;

    public Address(String street, String city, String ZIP, Restaurants restaurant) {
        this.street = street;
        this.city = city;
        this.ZIP = ZIP;
        this.restaurant = restaurant;
    }
}
