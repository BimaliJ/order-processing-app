package com.demo.order.app.mapper;

import com.demo.order.app.model.Order;
import com.demo.order.app.model.entities.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
    Order orderEntityToOrder(OrderEntity orderEntity);

    OrderEntity orderToOrderEntity(Order order);

    List<Order> orderEntitiesToOrderList(List<OrderEntity> orderEntities);
}
