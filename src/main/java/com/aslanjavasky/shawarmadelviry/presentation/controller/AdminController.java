package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.Order;
import com.aslanjavasky.shawarmadelviry.domain.model.OrderStatus;
import com.aslanjavasky.shawarmadelviry.presentation.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAdminPanel(Model model){
//        model.addAttribute("newOrders", orderService.getOrdersByStatus(OrderStatus.NEW));
        return "admin";
    }

    @PostMapping("/orders/update/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ){
        orderService.updateOrderStatus(id,status);
        return "redirect:/admin";
    }

    @GetMapping("/orders/filter")
    public String getOrdersFileterdByStatus(
            @RequestParam OrderStatus status,
            Model model
    ){
//        model.addAttribute("newOrders", orderService.getOrdersByStatus(OrderStatus.NEW));
        model.addAttribute("filteredOrders",orderService.getOrdersByStatus(status));
        return "admin";
    }

    @ModelAttribute(name = "newOrders")
    public List<Order> getNewOrders() {
        return orderService.getOrdersByStatus(OrderStatus.NEW);
    }





}
