package com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import com.aslanjavasky.shawarmadelviry.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository("MenuItemRepoExtPSRepo")
public interface MenuItemPSRepository extends PagingAndSortingRepository<MenuItemEntity, Long>,
                                                MenuItemRepository {

    @Override
    List<MenuItemEntity> findAll(Sort sort);

    @Override
    Page<MenuItemEntity> findAll(Pageable pageable);

    Page<MenuItemEntity> findByMenuSection(MenuSection menuSection, Pageable pageable);

    List<MenuItemEntity> findByMenuSectionOrderByPriceAsc(MenuSection menuSection, Sort sort);

    //PageRequest.of(0, 10)
    Page<MenuItemEntity> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);

    //Sort.of(Sort.Direction.ASC, "name")
    List<MenuItemEntity> findByPriceGreaterThanEqualOrderByNameAsc(BigDecimal price, Sort sort);

}
