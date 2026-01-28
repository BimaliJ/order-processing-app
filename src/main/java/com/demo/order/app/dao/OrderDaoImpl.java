package com.demo.order.app.dao;

import com.demo.order.app.dao.repositories.OrderRepository;
import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public class OrderDaoImpl implements OrderDao{
    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderEntity> searchOrderEntitiesByAttributes(OrderStatus status,
                                                      String customerName,
                                                      LocalDateTime createdAt,
                                                      LocalDateTime updatedAt,
                                                      Pageable pageable) throws OrderProcessingException {
        try {
            return orderRepository.findByStatusAndCustomerNameAndCreatedAtAndUpdatedAt(status,
                    customerName, createdAt, updatedAt, pageable);
        } catch (DataAccessException e) {
            throw new OrderProcessingException(e);
        }
    }
}
