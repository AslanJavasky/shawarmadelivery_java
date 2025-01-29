package com.aslanjavasky.shawarmadelviry.presentation.controller;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.presentation.service.MenuItemService;
import com.aslanjavasky.shawarmadelviry.presentation.service.SessionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuItemService service;

    public MenuController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public String showMenu(Model model) {
        Map<String, List<IMenuItem>> menuItemsBySection = Map.of(
                "menu.main_menu", service.getMenuItemsBySection(MenuSection.MAIN_MENU),
                "menu.zakuski", service.getMenuItemsBySection(MenuSection.ZAKUSKI),
                "menu.dobavki", service.getMenuItemsBySection(MenuSection.DOBAVKI),
                "menu.sauce", service.getMenuItemsBySection(MenuSection.SAUCE));
        model.addAttribute("menuItemsBySection", menuItemsBySection);
        return "menu";
    }

}
