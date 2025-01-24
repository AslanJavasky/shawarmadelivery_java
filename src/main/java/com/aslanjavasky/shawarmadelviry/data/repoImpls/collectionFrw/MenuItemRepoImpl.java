package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MenuItemRepoImpl implements MenuItemRepo {

    private final List<MenuItem> items = new ArrayList<>();

    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) {
        items.add(menuItem);
        return menuItem;
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        int index = items.indexOf(menuItem);
        if (index != -1) items.set(index, menuItem);
        return menuItem;
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return items.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MenuItem> getMenuItemsBySection(MenuSection section) {
        return items.stream()
                .filter(item -> item.getMenuSection().name().equals(section.name()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        items.remove(menuItem);
    }
}
