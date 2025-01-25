package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.presentation.service.DeliveryService;
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService;
import com.aslanjavasky.shawarmadelviry.presentation.service.OrderService;
import com.aslanjavasky.shawarmadelviry.presentation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class OrderAndDeliveryController {

    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;

    public OrderAndDeliveryController(MenuItemService menuItemService, OrderService orderService, DeliveryService deliveryService, UserService userService) {
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.userService = userService;
    }

    @PostMapping("/order")
    public String showOrderForm(
            @RequestParam List<Long> selectedId,
            @RequestParam List<Integer> quantities,
            Model model
    ) {
        List<MenuItem> selectedMenuItems = new ArrayList<>();
        for (int i = 0; i < selectedId.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                selectedMenuItems.add(menuItemService.getMenuItemById(selectedId.get(i)));
            }
        }
        BigDecimal totalPrice = selectedMenuItems.stream()
                .map(MenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setItemList(selectedMenuItems);
        order.setTotalPrice(totalPrice);
        order.setDateTime(LocalDateTime.now());

        Delivery delivery = new Delivery();

        User user=new User();

        model.addAttribute("delivery", delivery);
        model.addAttribute("order", order);
        model.addAttribute("user", user);

        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit(
            @ModelAttribute Delivery delivery,
            @ModelAttribute Order order,
            @ModelAttribute User user
    ) {
        log.info(String.valueOf(delivery));
        log.info(String.valueOf(order));
        log.info(String.valueOf(user));
        order.setUser(user);
        delivery.setOrder(order);

        orderService.createOrder(delivery.getOrder());
        deliveryService.createDelivery(delivery);
        userService.createUser(user);
        return "redirect:/menu";
    }
}
