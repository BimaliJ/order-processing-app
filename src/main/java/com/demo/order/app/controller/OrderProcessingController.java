package com.demo.order.app.controller;


import com.demo.order.app.model.Order;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("v1/orders")
public class OrderProcessingController {

    @PostMapping
    public void createOrder()
    {

    }

    @GetMapping
    public Order getOrderDetails()
    {
        return null;
    }

    @PutMapping
    public Order updateOrder()
    {
        return null;
    }

    public Page<Order> searchOrders(Order order,
                                    @PageableDefault(size=10)Pageable pageable)
    {

        return Page.empty(pageable);
    }

}
