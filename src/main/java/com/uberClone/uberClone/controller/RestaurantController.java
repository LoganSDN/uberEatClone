package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Restaurants;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantsService restaurantsService;

    @GetMapping("/all")
    public List<Restaurants> getAllRestaurants() {
        return restaurantsService.getAllRestaurant();
    }
}
