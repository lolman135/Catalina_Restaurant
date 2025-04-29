package com.coursework.app.catalinarestaurant.service.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.service.BaseService;

import java.util.Map;

public interface OrderService extends BaseService<Order> {
    String save(OrderDto request, Map<Long, Integer> cart);
    void updateStatusById(Long id, OrderStatus orderStatus);
}
