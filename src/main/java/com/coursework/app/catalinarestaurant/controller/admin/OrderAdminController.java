package com.coursework.app.catalinarestaurant.controller.admin;

import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.service.order.OrderService;
import com.coursework.app.catalinarestaurant.service.orderview.canceled.CanceledOrderViewService;
import com.coursework.app.catalinarestaurant.service.orderview.delivered.DeliveredOrderViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/catalina-restaurant/admin/orders")
public class OrderAdminController {

    private final OrderService orderService;
    private final CanceledOrderViewService canceledService;
    private final DeliveredOrderViewService deliveredService;

    public OrderAdminController(
            OrderService orderService,
            CanceledOrderViewService canceledService,
            DeliveredOrderViewService deliveredService)
    {
        this.orderService = orderService;
        this.canceledService = canceledService;
        this.deliveredService = deliveredService;
    }

    @GetMapping()
    public String showAllOrders(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "admin-order";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        orderService.deleteById(id);
        return "redirect:/catalina-restaurant/admin/orders";
    }

    @PutMapping("/update/{id}")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus orderStatus,
            RedirectAttributes redirectAttributes
    ){
        try {
            orderService.updateStatusById(id, orderStatus);
            redirectAttributes.addFlashAttribute("success", "Status updated successfully");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("error",
                    "Failed to update price: " + e.getMessage());
        }
        return "redirect:/catalina-restaurant/admin/orders";
    }

    @GetMapping("/statistic")
    public String showStatistic(@RequestParam(name = "type", defaultValue = "canceled") String type, Model model) {
        List<?> orders;
        switch (type) {
            case "delivered":
                orders = deliveredService.findAll();
                break;
            case "canceled":
                orders = canceledService.findAll();
                break;
            default:
                orders = Collections.emptyList();
                type = "canceled";
                break;
        }
        model.addAttribute("orderViews", orders);
        model.addAttribute("type", type);
        return "admin-order-statistic";
    }
}