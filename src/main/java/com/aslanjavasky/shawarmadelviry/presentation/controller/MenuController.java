package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService;
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService;
import com.aslanjavasky.shawarmadelviry.presentation.service.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemService menuService;
    private final SessionInfoService sessionInfoService;

    public MenuController(MenuItemService menuService, SessionInfoService sessionInfoService) {
        this.menuService = menuService;
        this.sessionInfoService = sessionInfoService;
    }


    @GetMapping
    public String showMenu(Model model) {
        Map<String, List<IMenuItem>> menuItemsBySection = Map.of(
                "menu.main_menu", menuService.getMenuItemsBySection(MenuSection.MAIN_MENU),
                "menu.zakuski", menuService.getMenuItemsBySection(MenuSection.ZAKUSKI),
                "menu.dobavki", menuService.getMenuItemsBySection(MenuSection.DOBAVKI),
                "menu.sauce", menuService.getMenuItemsBySection(MenuSection.SAUCE));
        model.addAttribute("menuItemsBySection", menuItemsBySection);
        log.info("sessionInfoService.getEmail():"+sessionInfoService.getEmail());
        return "menu";
    }

    @PostMapping("/order")
    public String processOrderForm(
            @RequestParam(required = false) List<Long> selectedId,
            @RequestParam(required = false) List<Integer> quantities,
            Model model
    ) {
        if (selectedId == null || selectedId.isEmpty()){
            model.addAttribute(
                    "error","Please, select at least 1 item to place an order.");
            return showMenu(model);
        }

        List<IMenuItem> selectedMenuItems = new ArrayList<>();
        for (int i = 0; i < selectedId.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                selectedMenuItems.add(menuService.getMenuItemById(selectedId.get(i)));
            }
        }
        sessionInfoService.setCart(selectedMenuItems);
//        model.addAttribute("sessionInfoService", sessionInfoService);
//        model.addAttribute("orderDto", new OrderDto());
        return "redirect:/order";
    }



}
