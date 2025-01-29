package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.*;
import com.aslanjavasky.shawarmadelviry.presentation.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class OrderAndDeliveryController {

    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;
    private final SessionInfoService sessionInfoService;

    public OrderAndDeliveryController(MenuItemService menuItemService, OrderService orderService, DeliveryService deliveryService, UserService userService, SessionInfoService sessionInfoService) {
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.deliveryService = deliveryService;
        this.userService = userService;
        this.sessionInfoService = sessionInfoService;
    }

    @GetMapping("/order")
    public String showOrderForm(Model model){
        if (sessionInfoService.getCart() == null || sessionInfoService.getCart().isEmpty()){
            return "redirect:/menu";
        }
        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order")
    public String processOrderForm(
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
        sessionInfoService.setCart(selectedMenuItems);
        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit() {
        log.info(String.valueOf(sessionInfoService));

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

        Delivery delivery = new Delivery();
        delivery.setDateTime(LocalDateTime.now());
        delivery.setPhone(sessionInfoService.getPhone());
        delivery.setAddress(sessionInfoService.getAddress());
        delivery.setOrder(order);

        orderService.createOrder(delivery.getOrder());
        deliveryService.createDelivery(delivery);
        userService.createUser(user);
        return "redirect:/menu";
    }
}
