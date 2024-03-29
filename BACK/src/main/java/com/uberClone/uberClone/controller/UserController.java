package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.services.interfaces.UsersService;
import jakarta.annotation.security.RolesAllowed;
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
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UsersService userService;

    @GetMapping("/all")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        User user = userService.getUserById(Long.parseLong(id)).orElse(null);
        if (user == null) {
            throw new EntityNotFoundException();
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User newUser) throws ServerException {
        User user = userService.createUser(newUser);
        if (user.equals(null)) {
            throw new ServerException("Unable to create user");
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }
}
