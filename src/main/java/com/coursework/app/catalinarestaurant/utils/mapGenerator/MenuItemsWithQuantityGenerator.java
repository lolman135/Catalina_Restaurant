package com.coursework.app.catalinarestaurant.utils.mapGenerator;

import com.coursework.app.catalinarestaurant.entity.MenuItem;

import java.util.Map;

public interface MenuItemsWithQuantityGenerator {

    Map<MenuItem, Integer> getMap(Map<Long, Integer> cart);
}