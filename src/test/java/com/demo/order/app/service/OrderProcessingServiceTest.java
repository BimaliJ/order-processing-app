package com.demo.order.app.service;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.mapper.OrderMapper;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import com.demo.order.app.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderProcessingServiceTest
{
    @InjectMocks
    private OrderProcessingService orderProcessingService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private NotificationDispatcher notificationDispatcher;

    @Test
    void test_createOrder_success()
    {

        String orderName = "Order1";
        String customerName = "Ann Loran";

        OrderEntity entity = OrderEntity.builder()
                .id(1L)
                .orderName(orderName)
                .customerName(customerName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        Order order = Order.builder()
                .orderName(orderName)
                .customerName(customerName)
                .build();

        when(orderMapper.orderToOrderEntity(any(Order.class)))
                .thenReturn(entity);
        when(orderRepository.save(any())).thenReturn(entity);
        when(orderMapper.orderEntityToOrder(entity)).thenReturn(order);

        orderProcessingService.createOrder(order);

        verify(orderRepository).save(entity);
        verify(notificationDispatcher).notifyOrderCreated(any(Order.class));
    }

    @Test
    void test_updateOrder_success() throws Exception
    {
        OrderEntity entity = new OrderEntity();
        entity.setId(1L);
        entity.setOrderStatus(OrderStatus.CREATED);

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(orderMapper.orderEntityToOrder(entity))
                .thenReturn(Order.builder()
                        .id(1L)
                        .orderStatus(OrderStatus.COMPLETED)
                        .build());

        Order updated =
                orderProcessingService.updateOrder(1L, OrderStatus.COMPLETED);

        assertThat(updated.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
        verify(notificationDispatcher).notifyOrderUpdated(any(Order.class));
    }

    @Test
    void test_updateOrder_notFound()
    {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderProcessingService.updateOrder(1L, OrderStatus.COMPLETED))
                .isInstanceOf(OrderProcessingException.class)
                .hasMessageContaining("Order not found with id : ",1L);
    }
}
