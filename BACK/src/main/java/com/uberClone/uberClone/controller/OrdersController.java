package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Orders;
import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.services.interfaces.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getOrders() {
        return new ResponseEntity<>(ordersService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping(path = "/new-order",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> createNewOrder(@RequestBody Orders newOrder) {
        return new ResponseEntity<>(this.ordersService.createOrder(newOrder), HttpStatus.CREATED);
    }
}
