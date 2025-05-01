package com.coursework.app.catalinarestaurant.repository.order;

import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    void updateOrderStatusById_ShouldUpdateStatus(){
        Order order = new Order(
                "Test Name",
                "+380991122333",
                "Test Address",
                OrderStatus.PENDING,
                null,
                0.0,
                LocalTime.now(),
                null
        );

        order = orderRepository.save(order);

        orderRepository.updateOrderStatusById(order.getId(), OrderStatus.CONFIRMED);
        entityManager.clear();

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        assertTrue(updatedOrder.isPresent());
        assertEquals(OrderStatus.CONFIRMED, updatedOrder.get().getOrderStatus());
    }

}
