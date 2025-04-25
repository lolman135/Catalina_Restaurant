package com.coursework.app.catalinarestaurant.mapper.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;

import java.io.IOException;

public interface MenuItemMapper {
    MenuItem toMenuItem(MenuItemDto request) throws IOException;
}
