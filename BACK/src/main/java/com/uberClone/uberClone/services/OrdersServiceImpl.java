package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Orders;
import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.repositories.OrdersRepository;
import com.uberClone.uberClone.services.interfaces.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public void createOrder(Users user, Orders order) {
        order.setUser(user);
        this.ordersRepository.save(order);
    }

    @Override
    public void updateOrder(String id, Orders order) {
        this.ordersRepository.save(order);
    }

    @Override
    public void cancelOrder(String id) {
        this.ordersRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<Orders> getOrdersByUser(Users user) {
        return null;
    }

    @Override
    public List<Orders> getAllOrders() {
        return null;
    }
}
