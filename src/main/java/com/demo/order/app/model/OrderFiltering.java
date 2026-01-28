package com.demo.order.app.model;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderFiltering {

    public static Specification<Order> hasCustomer(String customerId) {
        return (root, query, cb) ->
                customerId == null ? null :
                        cb.equal(root.get("customerId"), customerId);
    }

    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("orderStatus"), status);
    }

    public static Specification<Order> createdAt(
            LocalDateTime createdAt) {

        return (root, query, cb) -> {
            if (createdAt == null) return null;
            return cb.equal(
                    root.get("createdAt"),
                    createdAt);
        };
    }

    public static Specification<Order> updatedAt(
            LocalDateTime updatedAt) {

        return (root, query, cb) -> {
            if (updatedAt == null) return null;
            return cb.equal(
                    root.get("updatedAt"),
                    updatedAt);
        };
    }
}
