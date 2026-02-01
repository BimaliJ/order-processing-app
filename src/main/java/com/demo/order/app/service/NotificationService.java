package com.demo.order.app.service;

import com.demo.order.app.model.Order;

public interface NotificationService
{
    void notifyOrderCreated(Order order);

    void notifyOrderUpdated(Order order);
}
