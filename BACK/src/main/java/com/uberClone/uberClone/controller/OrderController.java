package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.services.UsersServiceImpl;
import com.uberClone.uberClone.services.interfaces.OrderService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService ordersService;
    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping(path = "/new-order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed({"ROLE_CUSTOMER", "ROLE_ADMIN"})
    public ResponseEntity<Order> createNewOrder(@RequestBody Order newOrder) {
        System.out.println(newOrder.toString());
        Order savedOrder = this.ordersService.createOrder(newOrder);
        if (savedOrder == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.usersService.findDriverForOrder(savedOrder);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return ordersService.getOrderById(id);
    }
}
