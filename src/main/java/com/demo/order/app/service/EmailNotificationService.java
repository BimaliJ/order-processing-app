package com.demo.order.app.service;

import com.demo.order.app.exception.EmailClientException;
import com.demo.order.app.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service("emailNotificationService")
public class EmailNotificationService implements NotificationService
{

    @Override
    @Async
    @Retryable(
            retryFor = EmailClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void notifyOrderCreated(Order order)
    {
        // call external email service
        // log email sent success
        log.info("[EMAIL] Order CREATED -> ID: {}, Name: {}, Customer: {}",
                order.getId(), order.getOrderName(), order.getCustomerName());
    }

    @Override
    @Async
    @Retryable(
            retryFor = EmailClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void notifyOrderUpdated(Order order)
    {
        // call external email service
        // log email sent success
        log.info("[EMAIL] Order UPDATED -> ID: {}, Status: {}",
                order.getId(), order.getOrderStatus());
    }
}

