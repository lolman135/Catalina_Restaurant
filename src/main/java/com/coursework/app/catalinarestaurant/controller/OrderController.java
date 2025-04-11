package com.coursework.app.catalinarestaurant.controller;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.mapper.order.OrderMapper;
import com.coursework.app.catalinarestaurant.service.order.OrderService;
import com.coursework.app.catalinarestaurant.utils.mapGenerator.MenuItemsWithQuantityGenerator;
import com.coursework.app.catalinarestaurant.utils.infoGenerator.CartInfoGenerator;
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
    private final MenuItemsWithQuantityGenerator generator;
    private final OrderService orderService;

    public OrderController(
            MenuItemsWithQuantityGenerator generator,
            OrderMapper orderMapper,
            OrderService orderService)
    {
        this.generator = generator;
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
    public String newOrder(
            Model model,
            @ModelAttribute("cart") HashMap<Long, Integer> cart)
    {
        Map<MenuItem, Integer> menuItemIntegerMap = generator.getMap(cart);
        model.addAttribute("stringList", CartInfoGenerator.getDishInfo(menuItemIntegerMap));
        model.addAttribute("totalPrice", CartInfoGenerator.getTotalPriceInfo(menuItemIntegerMap));
        return "order-creation-form";
    }

    @PostMapping("/order/register")
    public String registerOrder(
            @ModelAttribute("order") OrderDto orderDto,
            @ModelAttribute("cart") Map<Long, Integer> cart)
    {
        Order order = orderMapper.toEntity(orderDto, cart);
        orderService.save(order);
        cart.clear();
        return "redirect:/catalina-restaurant/menu";
    }

    @PostMapping("/order/clean-cart")
    public String cleanCart(@ModelAttribute("cart") Map<Long, Integer> cart){
        cart.clear();
        return "redirect:/catalina-restaurant/menu";
    }
}
