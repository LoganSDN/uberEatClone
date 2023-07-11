package com.uberClone.uberClone.repositories;

import com.uberClone.uberClone.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
//    public Optional<Delivery[]> findByStatus();
}
