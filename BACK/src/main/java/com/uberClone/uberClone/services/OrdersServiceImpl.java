package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.entities.Product;
import com.uberClone.uberClone.entities.Restaurant;
import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.repositories.OrdersRepository;
import com.uberClone.uberClone.repositories.ProductRepository;
import com.uberClone.uberClone.services.interfaces.OrdersService;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    RestaurantsService restaurantsService;

    @Override
    public Order createOrder(Order order) {
        User user = this.usersService.getUsersById(order.getSusId());
        Restaurant res = this.restaurantsService.getRestaurantById(order.getResId());
        if (res == null)
            return null;
        if (order.getProductsList() == null)
            order.setProductsList(new ArrayList<>());
        try {
            order.setProductsList(this._parseProductsOrder(order.getProductsList()));
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setRestaurant(res);
        newOrder.setProductsList(order.getProductsList());
        newOrder.setStatus(order.getStatus());
        newOrder.setTotalAmount(order.getTotalAmount());
        newOrder.setDateOfOrder(LocalDateTime.now());
        newOrder.setSusId(order.getSusId());
        newOrder.setResId(order.getResId());
        System.out.println(newOrder);
        return this.ordersRepository.save(newOrder);
    }

    private List<Product> _parseProductsOrder(List<Product> productList) throws Exception {
        List<Product> newPrList = new ArrayList<Product>();
        for (Product product
                : productList) {
            if (product.getId() == null)
                throw new Exception("Id null");
            Product pr = productRepository.findById(product.getId()).orElse(null);
            if (pr == null) {
                throw new Exception("Product not found");
            }
            newPrList.add(pr);
        }
        return newPrList;
    }

    @Override
    public void updateOrder(String id, Order order) {
        this.ordersRepository.save(order);
    }

    @Override
    public void cancelOrder(String id) {
        this.ordersRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return this.ordersRepository.findById(id).orElse(null);
    }
}