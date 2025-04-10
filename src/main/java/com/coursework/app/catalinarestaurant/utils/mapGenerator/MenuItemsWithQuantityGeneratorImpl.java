package com.coursework.app.catalinarestaurant.utils.mapGenerator;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MenuItemsWithQuantityGeneratorImpl implements MenuItemsWithQuantityGenerator {

    MenuItemService service;

    public MenuItemsWithQuantityGeneratorImpl(MenuItemService service) {
        this.service = service;
    }

    @Override
    public Map<MenuItem, Integer> getMap(Map<Long, Integer> cart){
        Map<MenuItem, Integer> map = new HashMap<>();
        cart.forEach((id, quantity) -> {
            map.put(service.findById(id), quantity);
        });
        return map;
    }
}