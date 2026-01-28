package com.demo.order.app.model.entities;

import com.demo.order.app.model.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderName;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
