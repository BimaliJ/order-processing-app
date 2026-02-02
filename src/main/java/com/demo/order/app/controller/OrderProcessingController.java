package com.demo.order.app.controller;


import com.demo.order.app.exception.OrderProcessingException;
import com.demo.order.app.model.Order;
import com.demo.order.app.model.OrderStatus;
import com.demo.order.app.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This controller is used by the Thymeleaf views,
 * so that Order processing and notification app
 * can demo visually with the Web UI
 */
@Log4j2
@Controller
@RequestMapping("/ui/orders")
@RequiredArgsConstructor
public class OrderProcessingController
{

    @Autowired
    private OrderProcessingService orderProcessingService;

    /**
     * Create Order
     */
    @PostMapping
    public String createOrder(
            @RequestParam String orderName,
            @RequestParam String customerName,
            RedirectAttributes redirectAttributes)
    {
        orderProcessingService.createOrder(
                Order.builder()
                        .orderName(orderName)
                        .customerName(customerName)
                        .build());
        // order creation success visually represented with a message in UI
        redirectAttributes.addFlashAttribute(
                "successMessage", "Order created and notification sent.");
        return "redirect:/ui/orders";
    }


    /**
     * Update order status
     */
    @PostMapping("/{id}/status")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus orderStatus,
            RedirectAttributes redirectAttributes)
    {
        try
        {
            orderProcessingService.updateOrder(id, orderStatus);
            // order update success visually represented with a message in UI
            redirectAttributes.addFlashAttribute(
                    "successMessage", "Order status updated and notification sent.");

        }
        catch (OrderProcessingException e) {
            log.error("Order not found with the provided id : {}", id, e);
        }
        return "redirect:/ui/orders";
    }

    /**
     * Retrieve single order (optional, useful later)
     */
    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id, Model model,
                           RedirectAttributes redirectAttributes)
    {
        try
        {
            Order order = orderProcessingService.getOrderDetails(id);
            model.addAttribute("order", order);
        }
        catch (OrderProcessingException e) {
            log.error("Order not found with the provided id : {}", id, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Order not found");
        }
        return "order-details";
    }

    @GetMapping
    public String searchOrders(
            Order order, // <-- auto-bound from request params
            @PageableDefault(size = 5) Pageable pageable,
            Model model)
    {
        Page<Order> orders = orderProcessingService.searchOrders(order, pageable);

        model.addAttribute("orders", orders);
        model.addAttribute("orderFilter", order);

        return "orders";
    }
}
