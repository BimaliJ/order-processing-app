package com.demo.order.app.util;

import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderFilteringUtil
{
    public static Specification<OrderEntity> hasCustomer(String customerName) {
        return (root, query, cb) ->
                customerName == null ? null :
                        cb.like(cb.lower(root.get("customerName")),
                                "%" + customerName.toLowerCase() + "%");
    }

    public static Specification<OrderEntity> hasStatus(OrderStatus status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<OrderEntity> createdAt(LocalDateTime createdAt) {
        return (root, query, cb) ->
                createdAt == null ? null :
                        cb.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
    }

    public static Specification<OrderEntity> updatedAt(LocalDateTime updatedAt) {
        return (root, query, cb) ->
                updatedAt == null ? null :
                        cb.lessThanOrEqualTo(root.get("updatedAt"), updatedAt);
    }
}
