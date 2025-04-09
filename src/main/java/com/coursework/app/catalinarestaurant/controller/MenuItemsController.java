package com.coursework.app.catalinarestaurant.controller;

import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalina-restaurant")
public class MenuItemsController {

    private final MenuItemService menuItemService;

    public MenuItemsController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/menu")
    public String showMainPage(Model model){
        List<MenuItem> menuItemList = menuItemService.findAll();
        for (int i = 0; i < 2; i++) {
            menuItemService.findAll().forEach(element -> menuItemList.add(element));
        }
        model.addAttribute("menuItemList", menuItemList);
        return "menu-page";
    }
}
