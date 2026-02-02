package com.demo.order.app.service;

import com.demo.order.app.exception.SmsClientException;
import com.demo.order.app.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log4j2
@Service("smsNotificationService")
public class SmsNotificationService implements NotificationService
{

    @Override
    @Async
    @Retryable(
            retryFor = SmsClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void notifyOrderCreated(Order order)
    {
        // call external sms service
        // log sms sent success
        log.info("[SMS] Order CREATED -> ID: {}, Customer: {}",
                order.getId(), order.getCustomerName());
    }

    @Override
    @Async
    @Retryable(
            retryFor = SmsClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void notifyOrderUpdated(Order order)
    {
        // call external sms service
        // log sms sent success
        log.info("[SMS] Order UPDATED -> ID: {}, Status: {}",
                order.getId(), order.getOrderStatus());
    }
}

