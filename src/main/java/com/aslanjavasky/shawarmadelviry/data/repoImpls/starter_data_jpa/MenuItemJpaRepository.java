package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jpa.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByMenuSection(MenuSection menuSection);
}
