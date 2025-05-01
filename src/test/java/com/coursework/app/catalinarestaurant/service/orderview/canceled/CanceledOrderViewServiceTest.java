package com.coursework.app.catalinarestaurant.service.orderview.canceled;

import com.coursework.app.catalinarestaurant.entity.CanceledOrderView;
import com.coursework.app.catalinarestaurant.repository.orderview.CanceledOrderViewRepository;
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
public class CanceledOrderViewServiceTest {

    @Mock
    private CanceledOrderViewRepository repository;

    @InjectMocks
    private CanceledOrderViewServiceImpl service;

    private CanceledOrderView orderView;

    @BeforeEach
    void setUp(){
        orderView = new CanceledOrderView();
    }

    @Test
    void findAll_ShouldCallRepository() {
        List<CanceledOrderView> orderViews = Arrays.asList(new CanceledOrderView(), new CanceledOrderView());
        when(repository.findAll()).thenReturn(orderViews);

        List<CanceledOrderView> result = service.findAll();

        assertThat(result).isEqualTo(orderViews);
        verify(repository).findAll();
    }

    @Test
    void findById_ValidData_ShouldReturnCanceledOrderView(){
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(orderView));

        CanceledOrderView result = service.findById(id);
        assertThat(result).isEqualTo(orderView);
    }

    @Test
    void findById_NullId_ShouldThrowException(){
        assertThatThrownBy(() -> service.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wrong id provided!");
    }

    @Test
    void findById_WrongId_ShouldReturnNull(){
        Long wrongId = 2L;
        when(repository.findById(wrongId)).thenReturn(Optional.empty());

        CanceledOrderView result = service.findById(wrongId);
        assertThat(result).isNull();
    }
}
