package com.demo.order.app.service;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.mapper.OrderMapper;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import com.demo.order.app.repositories.OrderRepository;
import com.demo.order.app.util.OrderFilteringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
public class OrderProcessingService {
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

    public Order createOrder(Order order)
    {

        order.setOrderStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);
        // persist the new order into the DB
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        Order savedOrder = orderMapper.orderEntityToOrder(savedEntity);
        log.info("Order created successfully with id : {}, order name : {}, customer name : {}",
                savedOrder.getId(), savedOrder.getOrderName(), savedOrder.getCustomerName());
        // notification sent
        notificationDispatcher.notifyOrderCreated(order);
        return savedOrder;
    }

    public Order getOrderDetails(Long id) throws OrderProcessingException
    {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderProcessingException("Order not found with id : ", id));
        log.info("Order details successfully retrieved for id :{}", orderEntity.getId());
        return orderMapper.orderEntityToOrder(orderEntity);
    }

    public Order updateOrder(Long id, OrderStatus orderStatus) throws OrderProcessingException
    {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new OrderProcessingException("Order not found with id : ", id));
        entity.setOrderStatus(orderStatus);
        // persist the updated order to the DB
        orderRepository.save(entity);
        Order updatedOrder = orderMapper.orderEntityToOrder(entity);
        log.info("Order updated successfully for id : {}, order name : {}, with status : {}",
                updatedOrder.getId(), updatedOrder.getOrderName(), updatedOrder.getOrderStatus());
        //notification sent
        notificationDispatcher.notifyOrderUpdated(updatedOrder);
        return updatedOrder;
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