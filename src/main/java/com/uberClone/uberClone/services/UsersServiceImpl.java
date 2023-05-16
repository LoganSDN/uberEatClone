package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Restaurants;
import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.repositories.RestaurantRepository;
import com.uberClone.uberClone.repositories.UsersRepository;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public Users createUser(Users user) {
        return this.usersRepository.save(user);
    }

    @Override
    public void updateUser(String id, Users user) {
        this.usersRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        this.usersRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<Users> getAllUsers() {
        return this.usersRepository.findAll();
    }

    @Override
    public Users getUsersById(String id) {
        return this.usersRepository.findById(Long.parseLong(id)).orElse(null);
    }
}
