package com.uberClone.uberClone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String ZIP;
    private Long lat;
    private Long lng;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_res_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "address_user_id", referencedColumnName = "id")
    private User user;

    public Address(String street, String city, String ZIP, Long lat, Long lng) {
        this.street = street;
        this.city = city;
        this.ZIP = ZIP;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", ZIP='" + ZIP + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
