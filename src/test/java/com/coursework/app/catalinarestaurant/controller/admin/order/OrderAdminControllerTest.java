package com.coursework.app.catalinarestaurant.controller.admin.order;

import com.coursework.app.catalinarestaurant.config.SecurityConfig;
import com.coursework.app.catalinarestaurant.controller.admin.OrderAdminController;
import com.coursework.app.catalinarestaurant.enums.OrderStatus;
import com.coursework.app.catalinarestaurant.service.order.OrderService;
import com.coursework.app.catalinarestaurant.service.orderview.canceled.CanceledOrderViewService;
import com.coursework.app.catalinarestaurant.service.orderview.delivered.DeliveredOrderViewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderAdminController.class)
@Import({OrderAdminControllerTest.TestConfig.class, SecurityConfig.class})
class OrderAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CanceledOrderViewService canceledService;

    @Autowired
    private DeliveredOrderViewService deliveredService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OrderService orderService() {
            return Mockito.mock(OrderService.class);
        }

        @Bean
        public CanceledOrderViewService canceledService() {
            return Mockito.mock(CanceledOrderViewService.class);
        }

        @Bean
        public DeliveredOrderViewService deliveredService() {
            return Mockito.mock(DeliveredOrderViewService.class);
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showAllOrders_ShouldReturnViewWithOrders() throws Exception {
        when(orderService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/catalina-restaurant/admin/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-order"))
                .andExpect(model().attributeExists("orders"));

        verify(orderService).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteOrder_ShouldDeleteAndRedirect() throws Exception {
        mockMvc.perform(delete("/catalina-restaurant/admin/orders/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/orders"));

        verify(orderService).deleteById(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateStatus_ShouldUpdateAndRedirect_OnSuccess() throws Exception {
        mockMvc.perform(put("/catalina-restaurant/admin/orders/update/1")
                        .param("orderStatus", "DELIVERED")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/admin/orders"));

        verify(orderService).updateStatusById(1L, OrderStatus.DELIVERED);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateStatus_ShouldRedirectWithError_OnFailure() throws Exception {
        doThrow(new RuntimeException("Update failed"))
                .when(orderService).updateStatusById(any(), any());

        mockMvc.perform(put("/catalina-restaurant/admin/orders/update/1")
                        .param("orderStatus", "CANCELED")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("error"))
                .andExpect(redirectedUrl("/catalina-restaurant/admin/orders"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showStatistic_ShouldReturnCanceledView() throws Exception {
        when(canceledService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/catalina-restaurant/admin/orders/statistic").param("type", "canceled"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-order-statistic"))
                .andExpect(model().attributeExists("orderViews", "type"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showStatistic_ShouldReturnDeliveredView() throws Exception {
        when(deliveredService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/catalina-restaurant/admin/orders/statistic").param("type", "delivered"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-order-statistic"))
                .andExpect(model().attributeExists("orderViews", "type"));
    }

    @Test
    @WithMockUser
    void adminAccess_ShouldBeForbidden_ForNonAdmin() throws Exception {
        mockMvc.perform(get("/catalina-restaurant/admin/orders"))
                .andExpect(status().isForbidden());
    }
}