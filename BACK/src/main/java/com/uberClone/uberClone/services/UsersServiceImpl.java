package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Role;
import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.repositories.RoleRepository;
import com.uberClone.uberClone.repositories.UserRepository;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            System.out.println(role.getName());
            Role existingRole = roleRepository.findByName(role.getName()).orElse(null);
            System.out.println(existingRole);
            if (existingRole != null) {
                roles.add(existingRole);
            }
        }
        user.setRoles(roles);
        System.out.println("hello world" + user.getRoles());
        return userRepository.save(user);
    }

    @Override
    public void updateUser(String id, User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUsersById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

}
