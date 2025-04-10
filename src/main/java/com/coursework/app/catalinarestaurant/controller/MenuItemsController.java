package com.coursework.app.catalinarestaurant.controller;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalina-restaurant")
public class MenuItemsController {

    private final MenuItemService menuItemService;

    public MenuItemsController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/menu")
    public String showMainPage(Model model, HttpSession httpSession) {
        List<MenuItem> menuItemList = menuItemService.findAll();
        Map<Long, Integer> cart = (Map<Long, Integer>) httpSession.getAttribute("cart");
        if (cart == null){
            cart = new HashMap<>();
        }
        model.addAttribute("menuItemList", menuItemList);
        model.addAttribute("cart", cart);
        return "menu-page";
    }
}
