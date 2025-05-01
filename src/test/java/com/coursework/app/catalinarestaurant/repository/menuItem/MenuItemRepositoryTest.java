package com.coursework.app.catalinarestaurant.repository.menuItem;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void updateNameById_ShouldUpdateName(){
        MenuItem menuItem = new MenuItem(
                "Test name",
                "Test description",
                0.0,
                Category.SNACK
        );

        menuItem = menuItemRepository.save(menuItem);
        Long id = menuItem.getId();
        menuItemRepository.updateNameById(id, "Test name 2");

        entityManager.clear();

        Optional<MenuItem> updatedMenuItem = menuItemRepository.findById(id);
        assertTrue(updatedMenuItem.isPresent());
        assertEquals("Test name 2", updatedMenuItem.get().getName());
    }

    @Test
    @Transactional
    void updateDescriptionById_ShouldUpdateName(){
        MenuItem menuItem = new MenuItem(
                "Test name",
                "Test description",
                0.0,
                Category.SNACK
        );

        menuItem = menuItemRepository.save(menuItem);
        Long id = menuItem.getId();
        menuItemRepository.updateDescriptionById(id, "Test description 2");

        entityManager.clear();

        Optional<MenuItem> updatedMenuItem = menuItemRepository.findById(id);
        assertTrue(updatedMenuItem.isPresent());
        assertEquals("Test description 2", updatedMenuItem.get().getDescription());
    }

    @Test
    @Transactional
    void updatePriceById_ShouldUpdateName(){
        MenuItem menuItem = new MenuItem(
                "Test name",
                "Test description",
                0.0,
                Category.SNACK
        );

        menuItem = menuItemRepository.save(menuItem);
        Long id = menuItem.getId();
        menuItemRepository.updatePriceById(id, 10.0);

        entityManager.clear();

        Optional<MenuItem> updatedMenuItem = menuItemRepository.findById(id);
        assertTrue(updatedMenuItem.isPresent());
        assertEquals(10.0, updatedMenuItem.get().getPrice());
    }

    @Test
    @Transactional
    void updateCategoryById_ShouldUpdateName(){
        MenuItem menuItem = new MenuItem(
                "Test name",
                "Test description",
                0.0,
                Category.SNACK
        );

        menuItem = menuItemRepository.save(menuItem);
        Long id = menuItem.getId();
        menuItemRepository.updateCategoryById(id, Category.PIZZA);

        entityManager.clear();

        Optional<MenuItem> updatedMenuItem = menuItemRepository.findById(id);
        assertTrue(updatedMenuItem.isPresent());
        assertEquals(Category.PIZZA, updatedMenuItem.get().getCategory());
    }
}