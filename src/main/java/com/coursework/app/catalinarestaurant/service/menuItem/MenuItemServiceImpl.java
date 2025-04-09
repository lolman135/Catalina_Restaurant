package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.repository.menuItem.MenuItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("MenuItemServiceImpl")
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public String save(MenuItem menuItem) {
        if (menuItem == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }
        menuItemRepository.save(menuItem);
        return "Dish successfully added to the menu";
    }

    @Override
    public boolean deleteById(Long id) {
        menuItemRepository.deleteById(id);
        return true;
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public MenuItem findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        Optional<MenuItem> item = menuItemRepository.findById(id);
        return item.orElse(null);
    }
}
