package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Address;
import com.uberClone.uberClone.entities.Restaurant;
import com.uberClone.uberClone.repositories.RestaurantRepository;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantsServiceImpl implements RestaurantsService {
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public ResponseEntity<Restaurant> createRestaurant(Restaurant newRestaurant) {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setOrders(new ArrayList<>());
//        restaurant.setName(newRestaurant.getName());
//        restaurant.setOpeningTime(newRestaurant.getOpeningTime());
//        restaurant.setClosingTime(newRestaurant.getClosingTime());
        Address address = new Address();
        address.setStreet(newRestaurant.getAddress().getStreet());
        address.setCity(newRestaurant.getAddress().getCity());
        address.setZIP(newRestaurant.getAddress().getZIP());
        address.setLat(newRestaurant.getAddress().getLat());
        address.setLng(newRestaurant.getAddress().getLng());
        address.setRestaurant(newRestaurant);
        newRestaurant.setAddress(address);

        Restaurant createdRestaurant = restaurantRepository.save(newRestaurant);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }


    @Override
    public void updateRestaurant(String id, Restaurant restaurant) {
        this.restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) {
        this.restaurantRepository.deleteById(Long.parseLong(id));
    }

    public Restaurant getRestaurantById(Long id) {
        return this.restaurantRepository.findById(id).orElse(null);
    }
    @Override
    public List<Restaurant> getAllRestaurant() {
        return this.restaurantRepository.findAll();
    }
}
