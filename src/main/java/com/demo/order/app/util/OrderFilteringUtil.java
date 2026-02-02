package com.demo.order.app.util;

import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.model.entities.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class is the Util
 * class which includes Spring
 * data specification
 */
public class OrderFilteringUtil
{
    public static Specification<OrderEntity> hasCustomer(String customerName)
    {
        return (root, query, cb) ->
                customerName == null || customerName.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("customerName")),
                                "%" + customerName.toLowerCase() + "%");
    }

    public static Specification<OrderEntity> hasOrderName(String orderName)
    {
        return (root, query, cb) ->
                orderName == null || orderName.isBlank()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("orderName")),
                                "%" + orderName.toLowerCase() + "%");
    }

    public static Specification<OrderEntity> hasStatus(OrderStatus status)
    {
        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("orderStatus"), status);
    }
}
