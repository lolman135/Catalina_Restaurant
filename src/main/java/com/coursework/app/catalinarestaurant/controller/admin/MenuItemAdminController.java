package com.coursework.app.catalinarestaurant.controller.admin;

import com.coursework.app.catalinarestaurant.enums.Category;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/catalina-restaurant/admin/menu")

public class MenuItemAdminController {

    private final MenuItemService menuItemService;

    public MenuItemAdminController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public String showAllMenuItem(Model model){
        model.addAttribute("menuItems", menuItemService.findAll());
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
    public String deleteMenuItem(@RequestParam("id") Long id){
        menuItemService.deleteById(id);
        return "redirect:/catalina-restaurant/admin/menu";
    }
}
