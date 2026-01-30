package com.demo.order.app.util;

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
        orderProcessingService.createOrder("Order 1", "Ann Loran");
        orderProcessingService.createOrder("Order 2", "Mathew Feros");
        orderProcessingService.createOrder("Order 3", "Lola Hnry");
    }
}
