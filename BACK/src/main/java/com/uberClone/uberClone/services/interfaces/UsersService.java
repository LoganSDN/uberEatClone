package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersService {
    User createUser(User user);
    void updateUser(User user);
    void deleteUser(String id);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);

    Optional<User> getBySocketId(UUID id);

    Optional<User> getUserByEmail(String Email);
    void findDriverForOrder(Order order);
    public void removeRejectedList(Long orderId);

    public void addRejectedList(Long orderId, UUID userId);
    void updateSocketUser( User user, UUID socketId);
}
