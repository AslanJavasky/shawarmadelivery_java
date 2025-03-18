package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("MenuItemM_Cassandra")
public class MenuItemMapper {

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem) {
        if (iMenuItem == null) return null;
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(UUIDUtils.getUUIDFromLong(iMenuItem.getId()));
        entity.setName(iMenuItem.getName());
        entity.setMenuSection(iMenuItem.getMenuSection());
        entity.setPrice(iMenuItem.getPrice());
        return entity;
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        IMenuItem menuItem = new MenuItem();
        menuItem.setId(UUIDUtils.getLongFromUUID(menuItemEntity.getId()));
        menuItem.setName(menuItemEntity.getName());
        menuItem.setMenuSection(menuItemEntity.getMenuSection());
        menuItem.setPrice(menuItemEntity.getPrice());
        return menuItem;
    }
}
