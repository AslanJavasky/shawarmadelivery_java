package com.aslanjavasky.shawarmadelviry.domain.interractor;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import java.util.List;

public class MenuItemInterractor {

    private final MenuItemRepo repo;

    public MenuItemInterractor(MenuItemRepo repo) {
        this.repo = repo;
    }

    public IMenuItem saveMenuItem(IMenuItem menuItem){
        return repo.saveMenuItem(menuItem);
    }

    public IMenuItem updateMenuItem(IMenuItem menuItem){
        return repo.updateMenuItem(menuItem);
    }

    public List<IMenuItem> getMenuItemsBySection(MenuSection section){
        return repo.getMenuItemsBySection(section);
    }

    public IMenuItem getMenuItemById(Long id){
        return repo.getMenuItemById(id);
    }

}
