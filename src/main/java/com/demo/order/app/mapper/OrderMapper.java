package com.demo.order.app.mapper;

import com.demo.order.app.model.Order;
import com.demo.order.app.model.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface OrderMapper
{
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderEntityToOrder(OrderEntity orderEntity);

    Page<Order> orderEntitiesToOrders(Page<OrderEntity> orderEntities);
}
