package com.coursework.app.catalinarestaurant.controller;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.mapper.order.OrderMapper;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import com.coursework.app.catalinarestaurant.service.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/catalina-restaurant")
@SessionAttributes("cart")
public class OrderController {

    private final OrderMapper orderMapper;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    public OrderController(MenuItemService menuItemService, OrderMapper orderMapper, OrderService orderService) {
        this.menuItemService = menuItemService;
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @ModelAttribute("cart")
    public Map<Long, Integer> cart(){
        return new HashMap<>();
    }

    @PostMapping("/add-to-cart")
    public String addToCart(
            @RequestParam("itemId") Long itemId,
            @RequestParam("quantity") int quantity,
            @ModelAttribute("cart") Map<Long, Integer> cart)
    {
        cart.put(itemId, quantity);
        return "redirect:/catalina-restaurant/menu";
    }

    @GetMapping("/order/new")
    public String newOrder(Model model){
        model.addAttribute("menuItems", menuItemService.findAll());
        return "order-creation-form";
    }

    @PostMapping("/order/register")
    public String registerOrder(
            @ModelAttribute("order") OrderDto orderDto,
            @ModelAttribute("cart") Map<Long, Integer> cart)
    {
        Order order = orderMapper.toEntity(orderDto, cart);
        orderService.save(order);
        return "redirect:/catalina-restaurant/menu";
    }
}
