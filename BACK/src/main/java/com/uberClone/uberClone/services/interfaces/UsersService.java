package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.entities.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    User createUser(User user);
    void updateUser(String id, User user);
    void deleteUser(String id);
    List<User> getAllUsers();
    User getUsersById(Long id);

    Optional<User> getUserByEmail(String Email);
    void findDriverForOrder(Order order);
    void updateStatus(String Status, User user);
}
