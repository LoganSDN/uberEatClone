package com.uberClone.uberClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String role;

        // Be aware of mappedBy => /!\ Must have the SAME NAME as the field in Orders entity
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Orders> ordersList;

        public Users(String firstName, String lastName, String email, String role) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.role = role;
                this.ordersList = new ArrayList<>();
        }
}
