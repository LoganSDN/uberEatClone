package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Restaurant;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantsService {
    ResponseEntity<Restaurant> createRestaurant(Restaurant restaurant);
    void updateRestaurant(String id, Restaurant restaurant);
    void deleteRestaurant(String id);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getAllRestaurant();
}
