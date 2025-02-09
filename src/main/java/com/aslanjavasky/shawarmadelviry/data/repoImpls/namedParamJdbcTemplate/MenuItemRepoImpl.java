package com.aslanjavasky.shawarmadelviry.data.repoImpls.namedParamJdbcTemplate;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository("MRwNPJT")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final JdbcTemplate jdbcTemplate;

    public MenuItemRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "INSERT INTO menu_items (id, name, menu_section, price) VALUES(?,?,?,?) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name=EXCLUDED.name,menu_section=EXCLUDED.menu_section,price=EXCLUDED.price";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setLong(1, menuItem.getId());
                ps.setString(2, menuItem.getName());
                ps.setString(3, menuItem.getMenuSection().name());
                ps.setBigDecimal(4, menuItem.getPrice());
                return ps;
            }
        }, keyHolder);
        menuItem.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return menuItem;
    }


    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {

        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "UPDATE menu_items SET name=?, menu_section=?, price=? WHERE id=?;";

        int affectedRow = jdbcTemplate.update(
                sql,
                menuItem.getName(), menuItem.getMenuSection().name(), menuItem.getPrice(), menuItem.getId());


        if (affectedRow == 0) throw new RuntimeException("Failed to update menuItem, no rows affected");
        return menuItem;
    }


    @Override
    public IMenuItem getMenuItemById(Long id) {

        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        String sql = "SELECT * FROM menu_items WHERE id=?;";

//        return jdbcTemplate.queryForStream(
//                        sql,
//                        (rs, numRow) -> {
//                            MenuItem menuItem = new MenuItem();
//                            menuItem.setId(rs.getLong("id"));
//                            menuItem.setName(rs.getString("name"));
//                            menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
//                            menuItem.setPrice(rs.getBigDecimal("price"));
//
//                            return menuItem;
//                        },
//                        new Object[]{id})
//                .findFirst()
//                .orElse(null);


        return jdbcTemplate.queryForStream(
                        sql,
                        new BeanPropertyRowMapper<>(MenuItem.class),
                        id)
                .findFirst()
                .orElse(null);

//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
//            MenuItem menuItem = new MenuItem();
//            menuItem.setId(rs.getLong("id"));
//            menuItem.setName(rs.getString("name"));
//            menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
//            menuItem.setPrice(rs.getBigDecimal("price"));
//
//            return menuItem;
//        });
    }

    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        String sql = "SELECT * FROM menu_items WHERE menu_section = ?";
        return jdbcTemplate.queryForStream(
                sql,
                new BeanPropertyRowMapper<>(MenuItem.class),
                section.name()).map(item -> (IMenuItem) item).toList();

//        return jdbcTemplate.query(sql, new Object[]{section.name()}, (rs, numRow) -> {
//            MenuItem menuItem = new MenuItem();
//            menuItem.setId(rs.getLong("id"));
//            menuItem.setName(rs.getString("name"));
//            menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
//            menuItem.setPrice(rs.getBigDecimal("price"));
//            return menuItem;
//        });
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("Menu Item cannot be null");
        String sql = "DELETE FROM menu_items WHERE id=?";
        int affectedRow = jdbcTemplate.update(sql, menuItem.getId());
        if (affectedRow == 0) throw new RuntimeException("Failed to delete menuItem, no rows affected");

    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM menu_items";
        jdbcTemplate.update(sql);
    }
}
