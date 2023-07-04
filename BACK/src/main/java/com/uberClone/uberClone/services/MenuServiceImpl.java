package com.uberClone.uberClone.services;

import com.uberClone.uberClone.entities.Menu;
import com.uberClone.uberClone.entities.Product;
import com.uberClone.uberClone.entities.Restaurant;
import com.uberClone.uberClone.repositories.MenuRepository;
import com.uberClone.uberClone.repositories.ProductRepository;
import com.uberClone.uberClone.repositories.RestaurantRepository;
import com.uberClone.uberClone.services.interfaces.MenuService;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RestaurantRepository restaurantRepository;
    @Override
    public Menu createMenu(Restaurant restaurant, Menu newMenu) {
        Menu menu = new Menu();
        menu.setName(newMenu.getName());
        menu.setDescription(newMenu.getDescription());
        menu.setRestaurant(restaurant);

        List<Menu> menus = restaurant.getMenus();
        if (menus == null) {
            menus = new ArrayList<>();
        }
        menus.add(menu);
        restaurant.setMenus(menus);

        Menu createdMenu = menuRepository.save(menu);
        return createdMenu;
    }
    @Override
    public void updateMenu(String id, Menu menu) {
        this.menuRepository.save(menu);
    }
    @Override
    public List<Menu> getAllMenus() {
        return this.menuRepository.findAll();
    }
    @Override
    public Menu getOrderById(String id) {
        return this.menuRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @Override
    public Product addProduct(Menu menu, Product product) {
        System.out.println(product);
        List<Product> pr = menu.getProductsList();
        if (pr == null)
            pr = new ArrayList<>();
        Product newPr = new Product();
        newPr.setName(product.getName());
        newPr.setPrice(product.getPrice());
        newPr.setDescription(product.getDescription());
        newPr.setMenu(menu);
        pr.add(newPr);
        menu.setProductsList(pr);
        productRepository.save(newPr);
        return newPr;
    }


    @Override
    public Menu getMenuById(Long id) {
        return this.menuRepository.findById(id).orElse(null);
    }
}
