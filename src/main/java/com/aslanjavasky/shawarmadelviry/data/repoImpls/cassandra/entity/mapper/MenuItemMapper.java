package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("MenuItemM_Cassandra")
public class MenuItemMapper {

    private final ModelMapper modelMapper;

    public MenuItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem) {
        if (iMenuItem == null) return null;
        return modelMapper.map(iMenuItem, MenuItemEntity.class);

    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        return modelMapper.map(menuItemEntity, MenuItem.class);

    }
}
