package com.coursework.app.catalinarestaurant.service.menuItem;

import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.mapper.menuItem.MenuItemMapper;
import com.coursework.app.catalinarestaurant.repository.menuItem.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper mapper;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    private MenuItemDto menuItemDto;
    private MenuItem menuItem;

    @BeforeEach
    void setUp() {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "image.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        menuItemDto = new MenuItemDto(
                "Test name",
                "Test description",
                10.0,
                Category.SNACK,
                mockFile
        );

        menuItem = new MenuItem("Test name", "Test description", 10.0, Category.SNACK);
        menuItem.setImageUrl("http://localhost/uploads/image.jpg");
    }

    @Test
    void deleteById_ShouldCallRepository() {
        Long id = 1L;

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        boolean result = menuItemService.deleteById(id);

        verify(menuItemRepository, times(1)).deleteById(id);
        assertThat(result).isTrue();
    }

    @Test
    void findAll_ShouldReturnSortedList() {
        List<MenuItem> menuItems = Arrays.asList(new MenuItem(), new MenuItem(), new MenuItem());
        when(menuItemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(menuItems);

        List<MenuItem> result = menuItemService.findAll();

        assertThat(result).hasSize(3);
        verify(menuItemRepository).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void findById_ValidId_ShouldReturnMenuItem(){
        Long id = 1L;
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(menuItem));

        MenuItem result = menuItemService.findById(id);

        assertThat(result).isEqualTo(menuItem);
    }

    @Test
    void findById_NullId_ShouldThrowException(){
        assertThatThrownBy(() -> menuItemService.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void findById_WrongId_ShouldReturnNull(){
        Long wrongId = 2L;
        when(menuItemRepository.findById(wrongId)).thenReturn(Optional.empty());

        MenuItem result = menuItemService.findById(wrongId);
        assertThat(result).isNull();
    }

    @Test
    void save_ValidData_ShouldReturnSuccessMessage() throws IOException{
        when(mapper.toEntity(menuItemDto)).thenReturn(menuItem);
        when(menuItemRepository.save(menuItem)).thenReturn(menuItem);

        String result = menuItemService.save(menuItemDto);

        assertThat(result).isEqualTo("Dish successfully added to the menu");
        verify(menuItemRepository).save(menuItem);
    }

    @Test
    void save_NullRequest_ShouldThrowException(){
        assertThatThrownBy(() -> menuItemService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong data provided!");
    }

    @Test
    void updateCategoryById_ValidData_ShouldCallRepository() {
        Long id = 1L;
        Category category = Category.PIZZA;

        menuItemService.updateCategoryById(id, category);

        verify(menuItemRepository, times(1)).updateCategoryById(id, category);
    }

    @Test
    void updateCategoryById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateCategoryById(null, Category.PIZZA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateCategoryById_NonPositiveId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateCategoryById(0L, Category.PIZZA))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateCategoryById_NullCategory_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateCategoryById(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category must not be null!");
    }

    @Test
    void updateNameById_ValidData_ShouldCallRepository() {
        Long id = 1L;
        String name = "Espresso";

        menuItemService.updateNameById(id, name);

        verify(menuItemRepository, times(1)).updateNameById(id, name.trim());
    }

    @Test
    void updateNameById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateNameById(null, "Espresso"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateNameById_NonPositiveId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateNameById(0L, "Espresso"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateNameById_NullName_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateNameById(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be null or empty!");
    }

    @Test
    void updateNameById_EmptyName_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateNameById(1L, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be null or empty!");
    }

    @Test
    void updateDescriptionById_ValidData_ShouldCallRepository() {
        Long id = 1L;
        String description = "Rich and bold flavor";

        menuItemService.updateDescriptionById(id, description);

        verify(menuItemRepository, times(1)).updateDescriptionById(id, description.trim());
    }

    @Test
    void updateDescriptionById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateDescriptionById(null, "Description"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateDescriptionById_NonPositiveId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateDescriptionById(0L, "Description"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateDescriptionById_NullDescription_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateDescriptionById(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Description must not be null or empty!");
    }

    @Test
    void updateDescriptionById_EmptyDescription_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updateDescriptionById(1L, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Description must not be null or empty!");
    }

    @Test
    void updatePriceById_ValidData_ShouldCallRepository() {
        Long id = 1L;
        double price = 9.99;

        menuItemService.updatePriceById(id, price);

        verify(menuItemRepository, times(1)).updatePriceById(id, price);
    }

    @Test
    void updatePriceById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updatePriceById(null, 9.99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updatePriceById_NonPositiveId_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updatePriceById(0L, 9.99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updatePriceById_NegativePrice_ShouldThrowException() {
        assertThatThrownBy(() -> menuItemService.updatePriceById(1L, -5.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Price must not be negative!");
    }
}