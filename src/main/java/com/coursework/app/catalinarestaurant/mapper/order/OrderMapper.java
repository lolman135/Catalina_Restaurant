package com.coursework.app.catalinarestaurant.mapper.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.Order;

import java.util.Map;

public interface OrderMapper {
    Order toEntity(OrderDto dto, Map<Long, Integer> cart);
}
