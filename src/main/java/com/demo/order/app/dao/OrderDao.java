package com.demo.order.app.dao;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderDao
{
    Page<OrderEntity> searchOrderEntitiesByAttributes(OrderStatus status,
                                                      String customerName,
                                                      LocalDateTime createdAt,
                                                      LocalDateTime updatedAt,
                                                      Pageable pageable) throws OrderProcessingException;
}
