package com.uberClone.uberClone.repositories;

import com.uberClone.uberClone.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurants, Long> {
}
