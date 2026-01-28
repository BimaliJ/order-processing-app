package com.demo.order.app.dao.repositories;

import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

        Page<OrderEntity> findByStatusAndCustomerNameAndCreatedAtAndUpdatedAt(
                OrderStatus status,
                String customerName,
                LocalDateTime createdAt,
                LocalDateTime updatedAt,
                Pageable pageable
        );
    }


