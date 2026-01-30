package com.demo.order.app.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order
{
    private Long id;
    private String orderName;
    private OrderStatus orderStatus;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
