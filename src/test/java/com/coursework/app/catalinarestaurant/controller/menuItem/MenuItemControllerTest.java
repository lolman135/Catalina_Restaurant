package com.coursework.app.catalinarestaurant.controller.menuItem;


import com.coursework.app.catalinarestaurant.controller.MenuItemsController;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuItemsController.class)
@Import(MenuItemControllerTest.MenuItemServiceTestConfig.class)
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemService menuItemService;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem("Burger", "Tasty", 10.0, Category.SNACK);
        menuItem.setId(1L);
    }

    @Test
    @WithMockUser
    void showMainPage_ShouldReturnMenuPageWithItemsAndEmptyCartIfNotPresent() throws Exception {
        Mockito.when(menuItemService.findAll()).thenReturn(List.of(menuItem));

        mockMvc.perform(get("/catalina-restaurant/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu-page"))
                .andExpect(model().attributeExists("menuItemList"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attribute("menuItemList", hasSize(1)));
    }

    @Test
    @WithMockUser
    void showMainPage_ShouldReturnMenuPageWithExistingCart() throws Exception {
        Mockito.when(menuItemService.findAll()).thenReturn(List.of(menuItem));

        Map<Long, Integer> cart = new HashMap<>();
        cart.put(1L, 2);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("cart", cart);

        mockMvc.perform(get("/catalina-restaurant/menu").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("menu-page"))
                .andExpect(model().attributeExists("menuItemList"))
                .andExpect(model().attribute("cart", cart));
    }

    @TestConfiguration
    static class MenuItemServiceTestConfig {

        @Bean
        public MenuItemService menuItemService() {
            return Mockito.mock(MenuItemService.class);
        }
    }
}