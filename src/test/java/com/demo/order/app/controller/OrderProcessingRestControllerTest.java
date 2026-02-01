package com.demo.order.app.controller;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.service.OrderProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderProcessingRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderProcessingService orderProcessingService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateOrderStatus_success() throws Exception {

        Order order = Order.builder()
                .id(1L)
                .orderName("Order 1")
                .customerName("Ann Loran")
                .orderStatus(OrderStatus.COMPLETED)
                .build();

        when(orderProcessingService.updateOrder(1L, OrderStatus.COMPLETED))
                .thenReturn(order);

        mockMvc.perform(put("/api/orders/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("COMPLETED"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void updateOrderStatus_forbiddenForUser() throws Exception {

        mockMvc.perform(put("/api/orders/1/status")
                        .param("orderStatus", "COMPLETED"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateOrderStatus_orderNotFound() throws Exception {

        when(orderProcessingService.updateOrder(1L, OrderStatus.COMPLETED))
                .thenThrow(new OrderProcessingException("Not found"));

        mockMvc.perform(put("/api/orders/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isNotFound());
    }
}

