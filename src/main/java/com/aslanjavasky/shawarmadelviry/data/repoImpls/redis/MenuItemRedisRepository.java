package com.aslanjavasky.shawarmadelviry.data.repoImpls.redis;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.redis.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRedisRepository extends CrudRepository<MenuItemEntity, UUID> {
    List<MenuItemEntity> findByMenuSection(MenuSection menuSection);
}
