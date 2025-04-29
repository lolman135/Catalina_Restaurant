package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.service.BaseService;

import java.io.IOException;

public interface MenuItemService extends BaseService<MenuItem> {
    String save(MenuItemDto request) throws IOException;
    void updateNameById(Long id, String name);
    void updateDescriptionById(Long id, String description);
    void updatePriceById(Long id, double price);
    void updateCategoryById(Long id, Category category);
}
