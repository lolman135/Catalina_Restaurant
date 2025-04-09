package com.coursework.app.catalinarestaurant.mapper.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.entity.OrderItem;
import com.coursework.app.catalinarestaurant.entity.OrderStatus;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderMapperImpl implements OrderMapper {

    private final MenuItemService menuItemService;

    public OrderMapperImpl(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @Override
    public Order toEntity(OrderDto dto, Map<Long, Integer> cart) {
        Order newOrder = new Order();
        newOrder.setCustomerName(dto.customerName());
        newOrder.setCustomerPhone(dto.customerPhone());
        newOrder.setCustomerAddress(dto.customerAddress());
        newOrder.setCreatedAt(LocalTime.now());
        newOrder.setOrderStatus(OrderStatus.PENDING);

        List<OrderItem> orderItems = cart.entrySet().stream()
                .map(entry -> {
                    MenuItem menuItem = menuItemService.findById(entry.getKey());
                    return new OrderItem(menuItem, entry.getValue());
                }).toList();

        orderItems.forEach(orderItem -> orderItem.setOrder(newOrder));
        newOrder.setOrderItems(orderItems);

        double totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum();

        newOrder.setTotalPrice(totalPrice);
        return newOrder;
    }
}
