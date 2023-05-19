package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.services.interfaces.UsersService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UsersService userService;

    @Autowired
    private AuthenticationManager authentication;

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") String id) {
        Users user = userService.getUsersById(id);
        if (user.equals(null)) {
            throw new EntityNotFoundException();
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> create(@RequestBody Users newUser) throws ServerException {
        Users user = userService.createUser(newUser);
        System.out.println(user.toString());
        if (user.equals(null)) {
            throw new ServerException("Unable to create user");
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PostMapping(path = "/sign-in")
    public ResponseEntity<HttpStatus> sign_in(@RequestBody Users user) throws Exception {
//        Users storedUser = userRepository.getUserByLogin(user.getLogin());
//
//        if (storedUser == null || !passwordEncoder.matches(user.getPassword(), storedUser.getPassword())) {
//            throw new BadCredentialsException("Identifiants invalides");
//        }
//
//        Authentication authObject = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authObject);
//
//        return ResponseEntity.ok().build();
        System.out.println(user.toString());
        Users storedUser = this.userService.getUserByLogin(user.getLogin());
        if (user.equals(null))
            throw new Exception("Not found");
        System.out.println(storedUser.toString());
        Authentication authObject;
        try {
            authObject = authentication.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials");
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
