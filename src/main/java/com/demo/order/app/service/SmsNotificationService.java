package com.demo.order.app.service;

import com.demo.order.app.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service("smsNotificationService")
public class SmsNotificationService implements NotificationService
{

    @Override
    public void notifyOrderCreated(Order order) {
        log.info("[SMS] Order CREATED -> ID: {}, Customer: {}",
                order.getId(), order.getCustomerName());
    }

    @Override
    public void notifyOrderUpdated(Order order) {
        log.info("[SMS] Order UPDATED -> ID: {}, Status: {}",
                order.getId(), order.getOrderStatus());
    }
}

