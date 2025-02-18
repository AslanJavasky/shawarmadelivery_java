package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.presentation.service.*;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.OrderDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class OrderAndDeliveryController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;
    private final SessionInfoService sessionInfoService;

    public OrderAndDeliveryController(OrderService orderService, DeliveryService deliveryService, UserService userService, SessionInfoService sessionInfoService) {
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.sessionInfoService = sessionInfoService;
    }

    @GetMapping("/order")
    public String showOrderForm(Model model){
        model.addAttribute("orderDto", new OrderDto(
                sessionInfoService.getUsername(),
                sessionInfoService.getAddress(),
                sessionInfoService.getPhone()));
        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit(
         @Valid @ModelAttribute("orderDto") OrderDto orderDto,
         BindingResult result,
         Model model
    ) {

        if (result.hasErrors()){
            model.addAttribute("sessionInfoService", sessionInfoService);
            model.addAttribute("orderDto",orderDto);
            return "order";
        }

        sessionInfoService.setInfoFromOrderDto(orderDto);

//        log.info(String.valueOf(sessionInfoService));
//        log.info("userService.getUserByEmail:"+userService.getUserByEmail(sessionInfoService.getEmail()));
//        log.info("sessionInfoService.getEmail():"+sessionInfoService.getEmail());

        IUser user = userService.getUserByEmail(sessionInfoService.getEmail());
        user.setAddress(sessionInfoService.getAddress());
        user.setName(sessionInfoService.getUsername());
        user.setPhone(sessionInfoService.getPhone());

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setItemList(sessionInfoService.getCart());
        order.setTotalPrice(sessionInfoService.getTotalPrice());
        order.setDateTime(LocalDateTime.now());
        order.setUser(user);
        Order savedOrder = (Order) orderService.createOrder(order);

        Delivery delivery = new Delivery();
        delivery.setDateTime(LocalDateTime.now());
        delivery.setPhone(sessionInfoService.getPhone());
        delivery.setAddress(sessionInfoService.getAddress());
        delivery.setOrder(savedOrder);

        deliveryService.createDelivery(delivery);
        userService.updateUser(user);
        return "redirect:/menu";
    }
}
