package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.*;
//import com.uberClone.uberClone.repositories.DriverViewRepository;
import com.uberClone.uberClone.repositories.DriverViewRepository;
import com.uberClone.uberClone.repositories.RoleRepository;
import com.uberClone.uberClone.repositories.UserRepository;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Autowired
    DriverViewRepository driverViewRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public UsersServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName()).orElse(null);
            System.out.println(existingRole);
            if (existingRole != null) {
                roles.add(existingRole);
            }
        }
        user.setRoles(roles);
        Address address = new Address();
        if (user.getAddress() != null) {
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setZIP(user.getAddress().getZIP());
            address.setUser(user);
            user.setAddress(address);
        }
        else {
            user.setAddress(null);
        }
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

    public void findDriverForOrder(Order order){
        List<DriverView> drivers= this.driverViewRepository.findAll();
//        parse to find closest / date mes couills
//        send websockmsg to usr
        drivers.forEach((d)-> System.out.println(d.toString()));
    }
}
