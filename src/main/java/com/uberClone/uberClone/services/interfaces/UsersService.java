package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    Users createUser(Users user);
    void updateUser(String id, Users user);
    void deleteUser(String id);
    List<Users> getAllUsers();
    Users getUsersById(String id);
}
