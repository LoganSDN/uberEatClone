package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.entities.Menu;
import com.uberClone.uberClone.entities.Product;
import com.uberClone.uberClone.services.interfaces.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PostMapping(path = "/{id}/new-product",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@PathVariable Long id, @RequestBody Product product) {
        Menu menu = this.menuService.getMenuById(id);
        if (menu == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(this.menuService.addProduct(menu, product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenu(@PathVariable Long id) {
        Menu menu = this.menuService.getMenuById(id);
        if (menu == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(menu, HttpStatus.OK);
    }
}
