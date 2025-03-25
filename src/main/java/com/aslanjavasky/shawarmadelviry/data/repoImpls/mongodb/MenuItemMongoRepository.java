package com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.mongodb.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemMongoRepository extends MongoRepository<MenuItemEntity, UUID> {
//    @Query("{'menuSection' : ?0 }")
    List<MenuItemEntity> findByMenuSection(MenuSection menuSection);
}
