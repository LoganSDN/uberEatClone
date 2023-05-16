package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Orders;
import com.uberClone.uberClone.entities.Users;

import java.util.List;

public interface OrdersService {
    void createOrder(Users user, Orders order);
    void updateOrder(String id, Orders order);
    void cancelOrder(String id);
    List<Orders> getOrdersByUser(Users user);
    List<Orders> getAllOrders();
}
