package com.coursework.app.catalinarestaurant.service.orderview.delivered;

import com.coursework.app.catalinarestaurant.entity.DeliveredOrderView;
import com.coursework.app.catalinarestaurant.repository.orderview.DeliveredOrderViewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DeliveredOrderViewServiceTest {

    @Mock
    private DeliveredOrderViewRepository repository;

    @InjectMocks
    private DeliveredOrderViewServiceImpl service;

    private DeliveredOrderView orderView;

    @BeforeEach
    void setUp() {
        orderView = new DeliveredOrderView();
    }

    @Test
    void findAll_ShouldCallRepository() {
        List<DeliveredOrderView> orderViews = Arrays.asList(new DeliveredOrderView(), new DeliveredOrderView());
        when(repository.findAll()).thenReturn(orderViews);

        List<DeliveredOrderView> result = service.findAll();

        assertThat(result).isEqualTo(orderViews);
        verify(repository).findAll();
    }

    @Test
    void findById_ValidData_ShouldReturnDeliveredOrderView() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(orderView));

        DeliveredOrderView result = service.findById(id);
        assertThat(result).isEqualTo(orderView);
    }

    @Test
    void findById_NullId_ShouldThrowException() {
        assertThatThrownBy(() -> service.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void findById_WrongId_ShouldReturnNull() {
        Long wrongId = 2L;
        when(repository.findById(wrongId)).thenReturn(Optional.empty());

        DeliveredOrderView result = service.findById(wrongId);
        assertThat(result).isNull();
    }
}
