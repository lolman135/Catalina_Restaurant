package com.coursework.app.catalinarestaurant.service.order;

import com.coursework.app.catalinarestaurant.dto.order.OrderDto;
import com.coursework.app.catalinarestaurant.entity.Order;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.mapper.order.OrderMapper;
import com.coursework.app.catalinarestaurant.repository.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDto orderDto;
    private Order order;
    private Map<Long, Integer> cart;

    @BeforeEach
    void setUp() {
        orderDto = new OrderDto(
                "Test Name",
                "+380999999999",
                "Test Address"
        );

        order = new Order();
        cart = new HashMap<>();
        cart.put(1L, 2);
    }

    @Test
    void deleteById_ShouldCallRepository() {
        Long id = 1L;

        boolean result = orderService.deleteById(id);

        verify(orderRepository, times(1)).deleteById(id);
        assertThat(result).isTrue();
    }

    @Test
    void findAll_ShouldReturnSortedList() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(orders);

        List<Order> result = orderService.findAll();

        assertThat(result).hasSize(2);
        verify(orderRepository).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void findById_ValidId_ShouldReturnOrder() {
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = orderService.findById(id);

        assertThat(result).isEqualTo(order);
    }

    @Test
    void findById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> orderService.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void findById_NotFound_ShouldReturnNull() {
        Long wrongId = 2L;
        when(orderRepository.findById(wrongId)).thenReturn(Optional.empty());

        Order result = orderService.findById(wrongId);
        assertThat(result).isNull();
    }

    @Test
    void save_ValidData_ShouldReturnSuccessMessage() {
        when(mapper.toEntity(orderDto, cart)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        String result = orderService.save(orderDto, cart);

        assertThat(result).isEqualTo("Order added successfully");
        verify(orderRepository).save(order);
    }

    @Test
    void save_NullRequest_ShouldThrowException() {
        assertThatThrownBy(() -> orderService.save(null, cart))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong data provided!");
    }

    @Test
    void updateStatusById_ValidData_ShouldCallRepository() {
        Long id = 1L;
        OrderStatus status = OrderStatus.DELIVERED;

        orderService.updateStatusById(id, status);

        verify(orderRepository).updateOrOrderStatusById(id, status);
    }

    @Test
    void updateStatusById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> orderService.updateStatusById(null, OrderStatus.DELIVERED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateStatusById_NonPositiveId_ShouldThrowException() {
        assertThatThrownBy(() -> orderService.updateStatusById(0L, OrderStatus.DELIVERED))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void updateStatusById_NullStatus_ShouldThrowException() {
        assertThatThrownBy(() -> orderService.updateStatusById(1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order status must not be null!");
    }
}