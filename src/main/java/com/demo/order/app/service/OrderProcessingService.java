package com.demo.order.app.service;

import com.demo.order.app.dao.OrderDao;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderFiltering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingService
{
    private final OrderDao orderDAO;

    public OrderProcessingService(OrderDao orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void createOrder()
    {

    }

    public Order retrieveOrderDetails()
    {
        return null;
    }

    public Order updateOrder()
    {
        return null;
    }

    public Page<Order> searchOrders(
                Order orderSearchRequest,
                Pageable pageable) {

            Specification<Order> spec = Specification
                    .where(OrderFiltering.hasCustomer(orderSearchRequest.getCustomerName()))
                    .and(OrderFiltering.hasStatus(orderSearchRequest.getOrderStatus()))
                    .and(OrderFiltering.createdAt(orderSearchRequest.getCreatedAt()))
                    .and(OrderFiltering.updatedAt(orderSearchRequest.getUpdatedAt()));


            //return orderRepository.findAll(spec, pageable);
        return Page.empty(pageable);
        }


}