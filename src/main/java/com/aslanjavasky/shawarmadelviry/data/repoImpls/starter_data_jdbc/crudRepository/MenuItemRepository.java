package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("MenuItemRepoExtCrudRepo")
public interface MenuItemRepository extends CrudRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> getMenuItemsByMenuSection(MenuSection menuSection);

    @Modifying
    @Query("INSERT INTO menu_items (id, name, menu_section, price) VALUES (:id, :name, :menu_section, :price)")
    void insert(@Param("id") Long id, @Param("name") String name,
                @Param("menu_section") MenuSection menuSection, @Param("price") BigDecimal price);
}
