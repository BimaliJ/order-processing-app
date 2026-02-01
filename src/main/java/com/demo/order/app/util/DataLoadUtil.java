package com.demo.order.app.util;

import com.demo.order.app.model.Order;
import com.demo.order.app.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoadUtil implements CommandLineRunner {

    private final OrderProcessingService orderProcessingService;

    @Override
    public void run(String... args) {
        Order oder1 = Order.builder().orderName("Order 1").customerName("Ann Loran").build();
        orderProcessingService.createOrder(oder1);
        Order oder2 = Order.builder().orderName("Order 2").customerName("Mathew Feros").build();

        orderProcessingService.createOrder(oder2);
        Order oder3 = Order.builder().orderName("Order 3").customerName("Lola Hnry").build();

        orderProcessingService.createOrder(oder3);
    }
}
