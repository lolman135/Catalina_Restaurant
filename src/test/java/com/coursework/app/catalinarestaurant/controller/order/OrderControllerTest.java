package com.coursework.app.catalinarestaurant.controller.order;

import com.coursework.app.catalinarestaurant.controller.OrderController;
import com.coursework.app.catalinarestaurant.service.order.OrderService;
import com.coursework.app.catalinarestaurant.utils.mapGenerator.MenuItemsWithQuantityGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MenuItemsWithQuantityGenerator generator;

    @InjectMocks
    private OrderController orderController;


    private MockHttpSession session;
    private Map<Long, Integer> cart;

    @BeforeEach
    void setUp() {
        cart = new HashMap<>();
        session = new MockHttpSession();
        session.setAttribute("cart", cart);

        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .build();
    }

    @Test
    @WithMockUser
    void addToCart_ShouldAddItemToCart() throws Exception {
        long itemId = 1L;
        int quantity = 2;

        mockMvc.perform(post("/catalina-restaurant/add-to-cart")
                        .param("itemId", String.valueOf(itemId))
                        .param("quantity", String.valueOf(quantity))
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/menu"));

        Map<Long, Integer> updatedCart = (Map<Long, Integer>) session.getAttribute("cart");
        assert updatedCart.containsKey(itemId);
        assert updatedCart.get(itemId) == quantity;
    }

    @Test
    @WithMockUser
    void newOrder_ShouldReturnOrderCreationForm() throws Exception {
        mockMvc.perform(get("/catalina-restaurant/order/new").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("order-creation-form"))
                .andExpect(model().attributeExists("order"))
                .andExpect(model().attributeExists("stringList"))
                .andExpect(model().attributeExists("totalPrice"));
    }


    @Test
    @WithMockUser
    void cleanCart_ShouldClearCart() throws Exception {
        cart.put(1L, 2);

        mockMvc.perform(post("/catalina-restaurant/order/clean-cart")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/catalina-restaurant/menu"));

        Map<Long, Integer> updatedCart = (Map<Long, Integer>) session.getAttribute("cart");
        assert updatedCart.isEmpty();
    }
}
