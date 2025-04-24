package com.coursework.app.catalinarestaurant.service.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.service.BaseService;

public interface OrderService extends BaseService<Order> {

    void updateStatusById(Long id, OrderStatus orderStatus);
}
