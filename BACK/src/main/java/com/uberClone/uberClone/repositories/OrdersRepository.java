package com.uberClone.uberClone.repositories;

import com.uberClone.uberClone.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
