package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Orders;
import com.uberClone.uberClone.services.interfaces.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
