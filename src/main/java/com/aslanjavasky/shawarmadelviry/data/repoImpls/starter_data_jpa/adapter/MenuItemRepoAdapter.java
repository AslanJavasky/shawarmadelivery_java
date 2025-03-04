package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.MenuItemJpaRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("MenuItemRepoAdapter_JPA")
public class MenuItemRepoAdapter implements MenuItemRepo {

    private final MenuItemJpaRepository menuItemRepository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(MenuItemJpaRepository menuItemRepository, MenuItemMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        return mapper.getIMenuItemFromMenuItemEntity(
                menuItemRepository.save(mapper.getMenuItemEntityFromIMenuItem(menuItem)));
    }


    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        return saveMenuItem(menuItem);
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).map(mapper::getIMenuItemFromMenuItemEntity).orElse(null);
    }

    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        return menuItemRepository.findByMenuSection(section).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        menuItemRepository.delete(mapper.getMenuItemEntityFromIMenuItem(menuItem));
    }

    @Override
    public void deleteAll() {
        menuItemRepository.deleteAll();
    }
}