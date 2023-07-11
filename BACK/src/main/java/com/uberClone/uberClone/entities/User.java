package com.uberClone.uberClone.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phoneNumber;

        // Be aware of mappedBy => /!\ Must have the SAME NAME as the field in Orders entity
        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Order> orderList;

        @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//    @JsonIgnore
        private Address address;

        @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
//    @JsonIgnore
        private Delivery delivery;

        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(
                name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();

        public User(Long id, String firstName, String lastName, String email, String password, List<Order> orderList, Set<Role> roles) {
                this.id = id;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.password = password;
                this.orderList = orderList;
                this.roles = roles;
        }

        @Override
        public String getUsername() {
                return this.email;
        }

        @Override
        public boolean isAccountNonExpired() {
                return true;
        }

        @Override
        public boolean isAccountNonLocked() {
                return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
                return true;
        }

        @Override
        public boolean isEnabled() {
                return true;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (Role role : roles) {
                        authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                return authorities;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }

        public void addRole(Role role) {
                this.roles.add(role);
        }
}
