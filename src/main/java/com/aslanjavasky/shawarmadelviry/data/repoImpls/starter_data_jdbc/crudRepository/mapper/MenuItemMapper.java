package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem){
        return new MenuItemEntity(
                iMenuItem.getId(),
                iMenuItem.getName(),
                iMenuItem.getMenuSection(),
                iMenuItem.getPrice()
        );
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity){
        return new MenuItem(
                menuItemEntity.getId(),
                menuItemEntity.getName(),
                menuItemEntity.getMenuSection(),
                menuItemEntity.getPrice()
        );
    }
}
