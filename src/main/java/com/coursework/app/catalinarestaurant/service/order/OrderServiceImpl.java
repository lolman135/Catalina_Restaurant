package com.coursework.app.catalinarestaurant.service.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("OrderServiceImpl")
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
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Wrong id provided!");
        }
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);

    }

    @Override
    public String save(Order order) {
        if (order == null){
            throw new IllegalArgumentException("Wrong data provided!");
        }
        orderRepository.save(order);
        return "Order added successfully";
    }
}
