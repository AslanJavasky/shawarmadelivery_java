package com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.adapter;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.UUIDUtils;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.MenuItemNeo4jRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.neo4j.entity.mapper.MenuItemMapper;
import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("MenuItemRepoAdapter_Neo4j")
public class MenuItemRepoAdapter implements MenuItemRepo {

    private final MenuItemNeo4jRepository menuItemRepository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(MenuItemNeo4jRepository menuItemRepository,
                               @Qualifier("MenuItemM_Neo4j") MenuItemMapper mapper) {
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
        return menuItemRepository.findById(UUIDUtils.getUUIDFromLong(id))
                .map(mapper::getIMenuItemFromMenuItemEntity).orElse(null);
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