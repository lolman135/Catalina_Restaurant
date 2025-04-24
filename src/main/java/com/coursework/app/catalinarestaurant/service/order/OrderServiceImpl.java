package com.coursework.app.catalinarestaurant.service.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean deleteById(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Order findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Order order) {
        if (order == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }
        orderRepository.save(order);
        return "Order added successfully";
    }

    @Override
    @Transactional
    public void updateStatusById(Long id, OrderStatus orderStatus) {
        if (id == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }

        orderRepository.updateOrOrderStatusById(id, orderStatus);
    }
}
