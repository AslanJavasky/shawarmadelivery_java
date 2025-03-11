//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq;
//
//import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
//import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
//import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
//import static com.aslanjavasky.shawarmadelviry.generated.jooq.tables.MenuItems.MENU_ITEMS;
//import org.jooq.DSLContext;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//
//
//@Repository("MRwJOOQ")
//public class MenuItemRepoImpl implements MenuItemRepo {
//
//
//    private final DSLContext dslContext;
//
//    public MenuItemRepoImpl(DSLContext dslContext) {
//        this.dslContext = dslContext;
//    }
//
//    @Override
//    public IMenuItem saveMenuItem(IMenuItem menuItem) {
//
//        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");
//
//        int affectedRow = dslContext.insertInto(MENU_ITEMS)
//                .set(MENU_ITEMS.ID, menuItem.getId())
//                .set(MENU_ITEMS.NAME, menuItem.getName())
//                .set(MENU_ITEMS.MENU_SECTION, menuItem.getMenuSection().name())
//                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
//                .onDuplicateKeyUpdate()
//                .set(MENU_ITEMS.NAME, menuItem.getName())
//                .set(MENU_ITEMS.MENU_SECTION, menuItem.getMenuSection().name())
//                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
//                .execute();
//        if (affectedRow == 0) throw new RuntimeException("Failed to save menuItem");
//        return menuItem;
//    }
//
//
//    @Override
//    public IMenuItem updateMenuItem(IMenuItem menuItem) {
//
//        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");
//
//        int affectedRow = dslContext.update(MENU_ITEMS)
//                .set(MENU_ITEMS.NAME, menuItem.getName())
//                .set(MENU_ITEMS.MENU_SECTION, menuItem.getMenuSection().name())
//                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
//                .where(MENU_ITEMS.ID.eq(menuItem.getId()))
//                .execute();
//        if (affectedRow == 0) throw new RuntimeException("Failed to update menuItem, no rows affected");
//        return menuItem;
//    }
//
//
//    @Override
//    public IMenuItem getMenuItemById(Long id) {
//
//        if (id == null) throw new IllegalArgumentException("Id cannot be null");
//
//        return dslContext.selectFrom(MENU_ITEMS).where(MENU_ITEMS.ID.eq(id)).fetchOneInto(MenuItem.class);
//    }
//
//    @Override
//    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
//
//        return dslContext.selectFrom(MENU_ITEMS)
//                .where(MENU_ITEMS.MENU_SECTION.eq(section.name()))
//                .fetchInto(MenuItem.class);
//    }
//
//    @Override
//    public void deleteMenuItem(IMenuItem menuItem) {
//        if (menuItem == null) throw new IllegalArgumentException("Menu Item cannot be null");
//
//        int affectedRow = dslContext.deleteFrom(MENU_ITEMS)
//                .where(MENU_ITEMS.ID.eq(menuItem.getId()))
//                .execute();
//        if (affectedRow == 0) throw new RuntimeException("Failed to delete menuItem, no rows affected");
//
//    }
//
//    @Override
//    public void deleteAll() {
//        int affectedRow = dslContext.deleteFrom(MENU_ITEMS).execute();
////        if (affectedRow == 0) throw new RuntimeException("Failed to delete all menuItems, no rows affected");
//    }
//}
