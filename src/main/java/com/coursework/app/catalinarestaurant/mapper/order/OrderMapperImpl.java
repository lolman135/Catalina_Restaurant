package com.coursework.app.catalinarestaurant.mapper.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.MenuItem;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.entity.OrderItem;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.repository.menuItem.MenuItemRepository;
import com.coursework.app.catalinarestaurant.service.menuItem.MenuItemService;
import com.coursework.app.catalinarestaurant.utils.number.NumberUtils;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component
public class OrderMapperImpl implements OrderMapper {

    private final MenuItemRepository menuItemRepository;

    public OrderMapperImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
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
                    MenuItem menuItem = menuItemRepository.findById(entry.getKey()).orElse(null);
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