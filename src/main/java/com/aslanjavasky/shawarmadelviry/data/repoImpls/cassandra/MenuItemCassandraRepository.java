package com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.cassandra.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemCassandraRepository extends CassandraRepository<MenuItemEntity, Long> {
    @Query("SELECT * FROM menu_items WHERE menu_section=?0 ALLOW FILTERING")
    List<MenuItemEntity> findByMenuSection(MenuSection menuSection);
}
