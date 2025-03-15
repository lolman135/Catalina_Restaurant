package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.repository.MenuItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("MenuItemServiceImpl")
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @Override
    public String save(MenuItem menuItem) {
        if (menuItem == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }
        menuItemsRepository.save(menuItem);
        return "Dish successfully added to the menu";
    }

    @Override
    public boolean deleteById(Long id) {
        menuItemsRepository.deleteById(id);
        return true;
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemsRepository.findAll();
    }

    @Override
    public MenuItem findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        Optional<MenuItem> item = menuItemsRepository.findById(id);
        return item.orElse(null);
    }
}
