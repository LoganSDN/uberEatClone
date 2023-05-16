package com.uberClone.uberClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
