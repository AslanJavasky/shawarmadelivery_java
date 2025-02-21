package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component("MenuItemRepoAdapter_PageSortING")
public class MenuItemRepoAdapter implements MenuItemRepo {
    private final MenuItemPSRepository menuItemPSRepository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(@Qualifier("MenuItemRepoExtPSRepo") MenuItemPSRepository menuItemPSRepository, MenuItemMapper mapper) {
        this.menuItemPSRepository = menuItemPSRepository;
        this.mapper = mapper;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        if (menuItem.getId() != null) {
            Optional<MenuItemEntity> optionalMenuItem = menuItemPSRepository.findById(menuItem.getId());
            if (optionalMenuItem.isPresent()) {
                //update
                return mapper.getIMenuItemFromMenuItemEntity(
                        menuItemPSRepository.save(mapper.getMenuItemEntityFromIMenuItem(menuItem)));
            } else {
                menuItemPSRepository.insert(
                        menuItem.getId(), menuItem.getName(), menuItem.getMenuSection(), menuItem.getPrice());
            }
        }
        //insert
        return mapper.getIMenuItemFromMenuItemEntity(
                menuItemPSRepository.save(mapper.getMenuItemEntityFromIMenuItem(menuItem)));
    }


    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        return saveMenuItem(menuItem);
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        return menuItemPSRepository.findById(id).map(mapper::getIMenuItemFromMenuItemEntity).orElse(null);
    }

    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        return menuItemPSRepository.getMenuItemsByMenuSection(section).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        menuItemPSRepository.delete(mapper.getMenuItemEntityFromIMenuItem(menuItem));
    }

    @Override
    public void deleteAll() {
        menuItemPSRepository.deleteAll();
    }


    public Page<IMenuItem> getAllMenuItems(Pageable pageable) {
        return menuItemPSRepository.findAll(pageable).map(mapper::getIMenuItemFromMenuItemEntity);
    }

    public List<IMenuItem> getAllMenuItems(Sort sort) {
        return menuItemPSRepository.findAll(sort).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity).toList();
    }

    public Page<IMenuItem> getMenuItemsBySection(MenuSection menuSection, Pageable pageable) {
        return menuItemPSRepository.findByMenuSection(menuSection, pageable)
                .map(mapper::getIMenuItemFromMenuItemEntity);
    }

    public List<IMenuItem> getMenuItemsBySectionOrderByPriceAsc(MenuSection menuSection,Sort sort){
        return menuItemPSRepository.findByMenuSectionOrderByPriceAsc(menuSection, sort).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .toList();
    }

    public Page<IMenuItem> getMenuItemsByPriceLessThanEqual(BigDecimal price, Pageable pageable){
        return menuItemPSRepository.findByPriceLessThanEqual(price, pageable)
                .map(mapper::getIMenuItemFromMenuItemEntity);
    }

    public List<IMenuItem> getMenuItemsByPriceGreaterThanEqualOrderByNameAsc(BigDecimal price, Sort sort){
        return menuItemPSRepository.findByPriceGreaterThanEqualOrderByNameAsc(price, sort).stream()
                .map(mapper::getIMenuItemFromMenuItemEntity).toList();
    }
}

