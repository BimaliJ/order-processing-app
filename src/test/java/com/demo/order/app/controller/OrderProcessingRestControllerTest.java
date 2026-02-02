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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderProcessingRestControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderProcessingService orderProcessingService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void test_updateOrderStatus_success() throws Exception
    {

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
    void test_updateOrderStatus_forbiddenForUser() throws Exception
    {

        mockMvc.perform(put("/api/orders/1/status")
                        .param("orderStatus", "COMPLETED"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void test_updateOrderStatus_orderNotFound() throws Exception
    {

        when(orderProcessingService.updateOrder(1L, OrderStatus.COMPLETED))
                .thenThrow(new OrderProcessingException("Not found"));

        mockMvc.perform(put("/api/orders/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void test_searchOrders_thenReturnSuccessfully() throws Exception
    {
        // given
        Order order = Order.builder()
                .id(1L)
                .orderName("Order1")
                .customerName("John")
                .orderStatus(OrderStatus.CREATED)
                .build();

        Page<Order> page =
                new PageImpl<>(List.of(order), PageRequest.of(0, 5), 1);

        when(orderProcessingService.searchOrders( any(Order.class), any(Pageable.class)))
                .thenReturn(page);

        // when + then
        mockMvc.perform(get("/api/orders")
                        .param("orderName", "Order1")
                        .param("customerName", "John")
                        .param("page", "0")
                        .param("size", "5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].orderName")
                        .value("Order1"))
                .andExpect(jsonPath("$.content[0].customerName")
                        .value("John"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }
}

