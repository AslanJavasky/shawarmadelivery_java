package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("MenuItemRepoAdapter_CRUD")
public class MenuItemRepoAdapter implements MenuItemRepo {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(MenuItemRepository menuItemRepository, MenuItemMapper mapper) {
        this.menuItemRepository = menuItemRepository;
        this.mapper = mapper;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        if (menuItem.getId() != null) {
            Optional<MenuItemEntity> optionalMenuItem = menuItemRepository.findById(menuItem.getId());
            if (optionalMenuItem.isPresent()) {
                //update
                return mapper.getIMenuItemFromMenuItemEntity(
                        menuItemRepository.save(mapper.getMenuItemEntityFromIMenuItem(menuItem)));
            }else {
               menuItemRepository.insert(
                       menuItem.getId(),menuItem.getName(),menuItem.getMenuSection(),menuItem.getPrice());
            }
        }
        //insert
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
        return menuItemRepository.getMenuItemsByMenuSection(section).stream()
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
