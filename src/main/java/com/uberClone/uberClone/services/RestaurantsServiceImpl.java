package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Restaurants;
import com.uberClone.uberClone.repositories.RestaurantRepository;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantsServiceImpl implements RestaurantsService {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(Restaurants restaurant) {
        this.restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurant(String id, Restaurants restaurant) {
        this.restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        this.restaurantRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<Restaurants> getAllRestaurant() {
        return this.restaurantRepository.findAll();
    }
}
