package com.uberClone.uberClone.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class AddressDto {
    private Long id;
    private String street;
    private String city;
    private String ZIP;
}
