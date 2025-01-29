package com.aslanjavasky.shawarmadelviry.data.repoImpls.collectionFrw;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MenuItemRepoImpl implements MenuItemRepo {

    private final List<IMenuItem> items = new ArrayList<>();
    private final AtomicLong nextId=new AtomicLong(1);

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        menuItem.setId(nextId.getAndIncrement());
        items.add(menuItem);
        return menuItem;
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        int index = items.indexOf(menuItem);
        if (index != -1) items.set(index, menuItem);
        return menuItem;
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        return items.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        return items.stream()
                .filter(item -> item.getMenuSection().name().equals(section.name()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        items.remove(menuItem);
    }
}
