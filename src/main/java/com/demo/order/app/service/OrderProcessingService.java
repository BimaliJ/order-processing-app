package com.demo.order.app.service;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.mapper.OrderMapper;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import com.demo.order.app.repositories.OrderRepository;
import com.demo.order.app.util.OrderFilteringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderProcessingService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final NotificationDispatcher notificationDispatcher;

    public OrderProcessingService(OrderRepository orderRepository,
                                  OrderMapper orderMapper, NotificationDispatcher notificationDispatcher)
    {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.notificationDispatcher = notificationDispatcher;
    }

    public void createOrder(String orderName, String customerName)
    {
        Order order = Order.builder().orderName(orderName)
                .customerName(customerName)
                .orderStatus(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
        orderRepository.save(orderEntity);
        // notification sent
        notificationDispatcher.notifyOrderCreated(order);
    }

    public Order getOrderDetails(Long id) throws OrderProcessingException {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderProcessingException("Order not found with id : ", id));
        return orderMapper.orderEntityToOrder(orderEntity);
    }

    public void updateOrder(Long id, OrderStatus orderStatus) throws OrderProcessingException {
            OrderEntity entity = orderRepository.findById(id)
                    .orElseThrow(() -> new OrderProcessingException("Order not found with id : ", id));
            entity.setOrderStatus(orderStatus);
            orderRepository.save(entity);
            Order updatedOrder = orderMapper.orderEntityToOrder(entity);
            //notification sent
            notificationDispatcher.notifyOrderUpdated(updatedOrder);
    }

    public Page<Order> searchOrders(
                Order orderSearchRequest,
                Pageable pageable) {

        Specification<OrderEntity> spec = Specification
                .where(OrderFilteringUtil.hasCustomer(orderSearchRequest.getCustomerName()))
                .and(OrderFilteringUtil.hasStatus(orderSearchRequest.getOrderStatus()))
                .and(OrderFilteringUtil.hasOrderName(orderSearchRequest.getOrderName()));

        Page<OrderEntity> entityPage = orderRepository.findAll(spec, pageable);
        return entityPage.map(orderMapper::orderEntityToOrder);
    }

}