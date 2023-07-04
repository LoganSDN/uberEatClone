package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.entities.Menu;
import com.uberClone.uberClone.entities.Product;
import com.uberClone.uberClone.entities.Restaurant;

import java.util.List;

public interface MenuService {
    Menu createMenu(Restaurant restaurant, Menu menu);
    void updateMenu(String id, Menu menu);
    List<Menu> getAllMenus();
    Menu getOrderById(String id);
    Product addProduct(Menu menu, Product product);
    Menu getMenuById(Long id);
}
