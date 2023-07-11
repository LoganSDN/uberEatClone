package com.uberClone.uberClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Order> orders;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "restaurant")
//    @JsonIgnore
    private Address address;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "restaurant")
    private List<Menu> menus;

    public Restaurant(Long id, String name, LocalTime openingTime, LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }
    @Override
    public String toString() {
        return "Restaurants{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", orders=" + orders.toString() +
//                ", address=" + address.toString() +
                ", openingTime=" + openingTime +
                ", closingTime=" + closingTime +
                '}';
    }
}
