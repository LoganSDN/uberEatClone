package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Restaurants;

import java.util.List;

public interface RestaurantsService {
    void createRestaurant(Restaurants restaurant);
    void updateRestaurant(String id, Restaurants restaurant);
    void deleteRestaurant(String id);
    List<Restaurants> getAllRestaurant();
}
