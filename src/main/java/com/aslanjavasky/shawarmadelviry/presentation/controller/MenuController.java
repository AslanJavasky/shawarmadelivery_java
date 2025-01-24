package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemService service;

    public MenuController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public String showMenu(Model model) {
        Map<MenuSection, List<MenuItem>> menuItemsBySection = Map.of(
                MenuSection.MAIN_MENU, service.getMenuItemsBySection(MenuSection.MAIN_MENU),
                MenuSection.ZAKUSKI, service.getMenuItemsBySection(MenuSection.ZAKUSKI),
                MenuSection.DOBAVKI, service.getMenuItemsBySection(MenuSection.DOBAVKI),
                MenuSection.SAUCE, service.getMenuItemsBySection(MenuSection.SAUCE));
        model.addAttribute("menuItemsBySection", menuItemsBySection);
        return "menu";
    }

}
