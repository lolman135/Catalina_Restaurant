package com.coursework.app.catalinarestaurant.controller.admin.menuItem;

import com.coursework.app.catalinarestaurant.config.SecurityConfig;
import com.coursework.app.catalinarestaurant.controller.admin.MenuItemAdminController;
import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;



@WebMvcTest(MenuItemAdminController.class)
@Import({MenuItemAdminControllerTest.TestConfig.class, SecurityConfig.class})

class MenuItemAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemService menuItemService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MenuItemService menuItemService() {
            return Mockito.mock(MenuItemService.class);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showAllMenuItem_ShouldAddMenuItemsToModel() throws Exception {
        List<MenuItem> mockItems = List.of(new MenuItem());
        when(menuItemService.findAll()).thenReturn(mockItems);

        mockMvc.perform(get("/catalina-restaurant/admin/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-menu"))
                .andExpect(model().attributeExists("menuItems"));

        verify(menuItemService).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateName_ShouldUpdateNameAndRedirect() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/menu/update/1/name")
                        .param("name", "Pizza Margherita")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).updateNameById(1L, "Pizza Margherita");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateName_ShouldFailValidation_WhenNameIsInvalid() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/menu/update/1/name")
                        .param("name", "123$$$")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateDescription_ShouldUpdateDescriptionAndRedirect() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/menu/update/1/description")
                        .param("description", "Delicious dish")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).updateDescriptionById(1L, "Delicious dish");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updatePrice_ShouldUpdatePriceAndRedirect() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/menu/update/1/price")
                        .param("price", "12.50")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).updatePriceById(1L, 12.5);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategory_ShouldUpdateCategoryAndRedirect() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/menu/update/1/category")
                        .param("category", "PIZZA")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).updateCategoryById(1L, Category.PIZZA);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteMenuItem_ShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(delete("/catalina-restaurant/admin/menu/delete/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).deleteById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showNewDishForm_ShouldReturnAddFormView() throws Exception {
        mockMvc.perform(get("/catalina-restaurant/admin/menu/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-new-menu-item"))
                .andExpect(model().attributeExists("menuItemDto"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addNewDish_ShouldSaveMenuItem_WhenValid() throws Exception {
        MenuItemDto dto = new MenuItemDto("Pizza", "Tasty", 12.5, Category.PIZZA, null);
        when(menuItemService.save(any())).thenReturn("Saved");

        mockMvc.perform(post("/catalina-restaurant/admin/menu/add")
                        .flashAttr("menuItemDto", dto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu"));

        verify(menuItemService).save(any(MenuItemDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addNewDish_ShouldFailValidationAndRedirectBack_WhenInvalid() throws Exception {
        MenuItemDto dto = new MenuItemDto("", "", -5.0, null, null);

        mockMvc.perform(post("/catalina-restaurant/admin/menu/add")
                        .flashAttr("menuItemDto", dto)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/menu/new"));
    }

    @Test
    @WithMockUser
    void adminAccess_ShouldBeForbidden_ForNonAdmin() throws Exception {
        mockMvc.perform(get("/catalina-restaurant/admin/menu"))
                .andExpect(status().isForbidden());
    }
}