package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Menu;
import com.uberClone.uberClone.entities.Restaurant;
import com.uberClone.uberClone.services.interfaces.MenuService;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantController {
    @Autowired
    private RestaurantsService restaurantsService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/all")
    public List<Restaurant> getAllRestaurants() {
        return restaurantsService.getAllRestaurant();
    }

    @PostMapping(path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantsService.createRestaurant(restaurant);
    }

    @PostMapping(path = "/{id}/menu/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> addMenu(@PathVariable Long id,
                                        @RequestBody Menu menu) {
        Restaurant restaurant = restaurantsService.getRestaurantById(id);
        if (restaurant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Menu createdMenu = menuService.createMenu(restaurant, menu);
        return new ResponseEntity<>(createdMenu, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable Long id) {
        return restaurantsService.getRestaurantById(id);
    }

}
