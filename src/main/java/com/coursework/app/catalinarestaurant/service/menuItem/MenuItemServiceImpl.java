package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.entity.OrderItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.mapper.menuItem.MenuItemMapper;
import com.coursework.app.catalinarestaurant.repository.menuItem.MenuItemRepository;
import com.coursework.app.catalinarestaurant.repository.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final MenuItemMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public MenuItemServiceImpl(
            MenuItemRepository menuItemRepository,
            MenuItemMapper mapper,
            OrderRepository orderRepository)
    {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
        this.orderRepository = orderRepository;
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
    @Transactional
    public boolean deleteById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Wrong id provided!"));

        List<Order> orders = menuItem.getOrderItems().stream().map(OrderItem::getOrder).distinct().toList();

        for (OrderItem orderItem : new ArrayList<>(menuItem.getOrderItems())) {
            Order order = orderItem.getOrder();
            order.getOrderItems().remove(orderItem);
            orderItem.setOrder(null);
            orderItem.setMenuItem(null);
        }
        menuItem.getOrderItems().clear();

        for (Order order : orders) {
            if (order.getOrderItems().isEmpty()) {
                orderRepository.delete(order);
            }
        }

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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Wrong id provided!");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category must not be null!");
        }
        menuItemRepository.updateCategoryById(id, category);
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String name) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Wrong id provided!");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty!");
        }
        menuItemRepository.updateNameById(id, name.trim());
    }

    @Override
    @Transactional
    public void updateDescriptionById(Long id, String description) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Wrong id provided!");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description must not be null or empty!");
        }
        menuItemRepository.updateDescriptionById(id, description.trim());
    }

    @Override
    @Transactional
    public void updatePriceById(Long id, double price) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Wrong id provided!");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative!");
        }
        menuItemRepository.updatePriceById(id, price);
    }
}