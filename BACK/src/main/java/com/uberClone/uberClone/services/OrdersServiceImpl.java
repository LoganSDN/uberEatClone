package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Orders;
import com.uberClone.uberClone.entities.Restaurants;
import com.uberClone.uberClone.entities.Users;
import com.uberClone.uberClone.repositories.OrdersRepository;
import com.uberClone.uberClone.services.interfaces.OrdersService;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    RestaurantsService restaurantsService;

    @Override
    public Orders createOrder(Orders order) {
        Users user = this.usersService.getUsersById(order.getUser().getId().toString());
        Restaurants res = this.restaurantsService.getRestaurantById(order.getRestaurant().getId().toString());
        if (res != null)
            res = restaurantsService.createRestaurant(res);

//        user = this.usersService.getUsersById(user.getId().toString());
        order.setUser(user);
        order.setRestaurant(res);
        return this.ordersRepository.save(order);
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
