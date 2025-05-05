package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.MenuItemJpaRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        return mapper.getIMenuItemFromMenuItemEntity(
                menuItemRepository.save(mapper.getMenuItemEntityFromIMenuItem(menuItem)));
    }


    @Transactional
    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        return saveMenuItem(menuItem);
    }

    @Transactional
    @Override
    public IMenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id).map(mapper::getIMenuItemFromMenuItemEntity).orElse(null);
    }

    @Transactional
    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        return menuItemRepository.findByMenuSection(section).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .toList();
    }

    @Transactional
    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        menuItemRepository.delete(mapper.getMenuItemEntityFromIMenuItem(menuItem));
    }

    @Transactional
    @Override
    public void deleteAll() {
        menuItemRepository.deleteAll();
    }
}