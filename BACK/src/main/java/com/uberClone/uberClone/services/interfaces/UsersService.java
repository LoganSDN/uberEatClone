package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.User;

import java.util.List;

public interface UsersService {
    User createUser(User user);
    void updateUser(String id, User user);
    void deleteUser(String id);
    List<User> getAllUsers();
    User getUsersById(Long id);
}
