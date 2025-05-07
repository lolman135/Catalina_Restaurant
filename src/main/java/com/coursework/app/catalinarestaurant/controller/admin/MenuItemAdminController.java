package com.coursework.app.catalinarestaurant.controller.admin;

import ch.qos.logback.classic.spi.EventArgUtil;
import com.coursework.app.catalinarestaurant.dto.menuItem.MenuItemDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import com.coursework.app.catalinarestaurant.utils.counter.Counter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalina-restaurant/admin/menu")

public class MenuItemAdminController {

    private final MenuItemService menuItemService;

    public MenuItemAdminController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String showAllMenuItem(Model model){
        model.addAttribute("menuItems", Counter.getMapOfItemNumbers(menuItemService.findAll()));
        return "admin-menu";
    }

    @PutMapping("/update/{id}/name")
    public String updateName(
            @PathVariable("id") Long id,
            @RequestParam
            @Pattern(
                    regexp = "^[А-Яа-яA-Za-zЁёІіЇїЪъЫы\\-\\s]{0,50}+$",
                    message = "Name should contains only letters"
            ) String name,
            RedirectAttributes redirectAttributes
    ) {
        try {
            menuItemService.updateNameById(id, name);
            redirectAttributes.addFlashAttribute("success", "Name updated successfully");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update name: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/menu";
    }

    @PutMapping("/update/{id}/description")
    public String updateDescription(
            @PathVariable("id") Long id,
            @RequestParam @NotBlank(message = "Description should not be empty") String description,
            RedirectAttributes redirectAttributes
    ){
        try {
            menuItemService.updateDescriptionById(id, description);
            redirectAttributes.addFlashAttribute("success", "Description updated successfully");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update description: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/menu";
    }

    @PutMapping("/update/{id}/price")
    public String updatePrice(
            @PathVariable("id") Long id,
            @RequestParam @Min(0) double price,
            RedirectAttributes redirectAttributes
    ){
        try {
            menuItemService.updatePriceById(id, price);
            redirectAttributes.addFlashAttribute("success", "Price updated successfully");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update price: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/menu";
    }

    @PutMapping("/update/{id}/category")
    public String updateCategory(
            @PathVariable("id") Long id,
            @RequestParam @NotNull Category category,
            RedirectAttributes redirectAttributes
    ){
        try {
            menuItemService.updateCategoryById(id, category);
            redirectAttributes.addFlashAttribute("success", "Category updated successfully");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update category: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/menu";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") Long id){
        menuItemService.deleteById(id);
        return "redirect:/catalina-restaurant/admin/menu";
    }

    @GetMapping("/new")
    public String showNewDishForm(Model model){
        MenuItemDto menuItemDto = new MenuItemDto("", "", 0.0, null, null);
        model.addAttribute("menuItemDto", menuItemDto);
        return "add-new-menu-item";
    }

    @PostMapping("/add")
    public String addNewDish(
            @Valid @ModelAttribute("menuItemDto") MenuItemDto request,
            BindingResult result,
            RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            String errorMessage = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/catalina-restaurant/admin/menu/new";
        }

        try {
            String saveMessage = menuItemService.save(request);
            redirectAttributes.addFlashAttribute("success", saveMessage);
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to create new dish: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/menu";
    }
}