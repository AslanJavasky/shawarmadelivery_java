package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    private final ModelMapper modelMapper;

    public MenuItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem) {
        if (iMenuItem == null) return null;
        return modelMapper.map(iMenuItem, MenuItemEntity.class);

//        return new MenuItemEntity(
//                iMenuItem.getId(),
//                iMenuItem.getName(),
//                iMenuItem.getMenuSection(),
//                iMenuItem.getPrice()
//        );
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        return modelMapper.map(menuItemEntity, IMenuItem.class);

//        return new MenuItem(
//                menuItemEntity.getId(),
//                menuItemEntity.getName(),
//                menuItemEntity.getMenuSection(),
//                menuItemEntity.getPrice()
//        );
    }
}
