package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.services.interfaces.UsersService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") String id) {
        Users user = userService.getUsersById(id);
        if (user.equals(null)) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> create(@RequestBody Users newUser) throws ServerException {
        System.out.println(newUser.toString());
        Users user = userService.createUser(newUser);
        if (user.equals(null)) {
            throw new ServerException("Unable to create user");
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
