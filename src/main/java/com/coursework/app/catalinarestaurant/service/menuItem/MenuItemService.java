package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.service.BaseService;

public interface MenuItemService extends BaseService<MenuItem> {

    void updateNameById(Long id, String name);
    void updateDescriptionById(Long id, String description);
    void updatePriceById(Long id, double price);
    void updateCategoryById(Long id, Category category);
}
