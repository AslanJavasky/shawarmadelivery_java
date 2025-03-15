package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemCassandraRepository extends CassandraRepository<MenuItemEntity, UUID> {
    @Query("SELECT * FROM menu_items WHERE menu_section=?0 ALLOW FILTERING")
    List<MenuItemEntity> findByMenuSection(MenuSection menuSection);
}
