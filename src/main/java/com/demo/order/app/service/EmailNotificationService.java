package com.demo.order.app.service;

import com.demo.order.app.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service("emailNotificationService")
public class EmailNotificationService implements NotificationService
{

    @Override
    @Async
    public void notifyOrderCreated(Order order) {
        log.info("[EMAIL] Order CREATED -> ID: {}, Name: {}, Customer: {}",
                order.getId(), order.getOrderName(), order.getCustomerName());
    }

    @Override
    @Async
    public void notifyOrderUpdated(Order order) {
        log.info("[EMAIL] Order UPDATED -> ID: {}, Status: {}",
                order.getId(), order.getOrderStatus());
    }
}

