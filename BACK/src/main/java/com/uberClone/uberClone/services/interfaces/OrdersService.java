package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.entities.User;

import java.util.List;

public interface OrdersService {
    Order createOrder(Order order);
    void updateOrder(String id, Order order);
    void cancelOrder(String id);
    List<Order> getOrdersByUser(User user);
    List<Order> getAllOrders();

    Order getOrderById(Long id);
}
