package com.demo.order.app.service;

import com.demo.order.app.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationDispatcher
{

    private final List<NotificationService> notificationServices;

    public void notifyOrderCreated(Order order)
    {
        notificationServices.forEach(service -> service.notifyOrderCreated(order));
    }

    public void notifyOrderUpdated(Order order)
    {
        notificationServices.forEach(service -> service.notifyOrderUpdated(order));
    }
}
