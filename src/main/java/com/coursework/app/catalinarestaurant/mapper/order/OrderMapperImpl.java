package com.coursework.app.catalinarestaurant.mapper.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.entity.OrderItem;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import com.coursework.app.catalinarestaurant.utils.number.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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

        double totalPrice = NumberUtils.round(orderItems.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum(), 2);

        newOrder.setTotalPrice(totalPrice);
        return newOrder;
    }
}
