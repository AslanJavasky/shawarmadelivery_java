package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import java.util.List;

public class MenuItemInterractor {

    private final MenuItemRepo repo;

    public MenuItemInterractor(MenuItemRepo repo) {
        this.repo = repo;
    }

    public MenuItem saveMenuItem(MenuItem menuItem){
        return repo.saveMenuItem(menuItem);
    }

    public MenuItem updateMenuItem(MenuItem menuItem){
        return repo.updateMenuItem(menuItem);
    }

    public List<MenuItem> getMenuItemsBySection(MenuSection section){
        return repo.getMenuItemsBySection(section);
    }

    public MenuItem getMenuItemById(Long id){
        return repo.getMenuItemById(id);
    }

}
