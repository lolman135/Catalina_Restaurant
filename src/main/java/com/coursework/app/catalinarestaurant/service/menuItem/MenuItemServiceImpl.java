package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.mapper.menuItem.MenuItemMapper;
import com.coursework.app.catalinarestaurant.repository.menuItem.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper mapper;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuItemMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Override
    public String save(MenuItemDto request) throws IOException {
        if (request == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }
        MenuItem menuItem = mapper.toEntity(request);
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
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void updateCategoryById(Long id, Category category) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        menuItemRepository.updateCategoryById(id, category);
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String name) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        menuItemRepository.updateNameById(id, name);
    }

    @Override
    @Transactional
    public void updateDescriptionById(Long id, String description) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        menuItemRepository.updateDescriptionById(id, description);
    }

    @Override
    @Transactional
    public void updatePriceById(Long id, double price) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        menuItemRepository.updatePriceById(id, price);
    }
}
