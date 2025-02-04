package com.aslanjavasky.shawarmadelviry.domain.repo;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import java.util.List;

public interface MenuItemRepo {
    IMenuItem saveMenuItem(IMenuItem menuItem);
    IMenuItem updateMenuItem(IMenuItem menuItem);
    IMenuItem getMenuItemById(Long id);
    List<IMenuItem> getMenuItemsBySection(MenuSection section);
    void deleteMenuItem(IMenuItem menuItem);
    void deleteAll();
}
