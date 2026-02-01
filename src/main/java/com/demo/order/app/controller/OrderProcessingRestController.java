package com.demo.order.app.controller;

import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderProcessingRestController
{
    private final OrderProcessingService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order)
    {
        Order created = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id)
    {
        try
        {
            Order order = orderService.getOrderDetails(id);
            return ResponseEntity.ok(order);
        }
        catch (OrderProcessingException e) {
            log.error("Order not found with the provided id : {}", id, e);
            return ResponseEntity.notFound().build();

        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status)
    {
        try {
            Order updated = orderService.updateOrder(id, status);
            return ResponseEntity.ok(updated);

        }
        catch (OrderProcessingException e) {
            log.error("Order not found with the provided id : {}", id, e);
            return ResponseEntity.notFound().build(); // 404

        }
    }

    @GetMapping
    public ResponseEntity<Page<Order>> searchOrders(Order filter, Pageable pageable)
    {
        Page<Order> orders = orderService.searchOrders(filter, pageable);
        return ResponseEntity.ok(orders);
    }
}

