package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import com.aslanjavasky.shawarmadelviry.domain.model.IMenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem;
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection;
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("MRwPS")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final DataSource dataSource;

    public MenuItemRepoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {

        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "INSERT INTO menu_items (name, menu_section, price) VALUES(?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            ps.setString(1, menuItem.getName());
            ps.setString(2, menuItem.getMenuSection().name());
            ps.setBigDecimal(3, menuItem.getPrice());

            int affectedRow= ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save menuItem, no rows affected");

            try (ResultSet rs= ps.getGeneratedKeys()){
                while(rs.next()){
                    menuItem.setId(rs.getLong("id"));
                }
            }

            return menuItem;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {

        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "UPDATE menu_items SET name=?, menu_section=?, price=? WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, menuItem.getName());
            ps.setString(2, menuItem.getMenuSection().name());
            ps.setBigDecimal(3, menuItem.getPrice());
            ps.setLong(4, menuItem.getId());

            int affectedRow= ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update menuItem, no rows affected");
            return menuItem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {

        if (id==null) throw new IllegalArgumentException("Id cannot be null");

        String sql = "SELECT * FROM menu_items WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            MenuItem menuItem = new MenuItem();
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    menuItem.setId(rs.getLong("id"));
                    menuItem.setName(rs.getString("name"));
                    menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
                    menuItem.setPrice(rs.getBigDecimal("price"));
                }
            }
            return menuItem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IMenuItem> getMenuItemsBySection(MenuSection section) {
        String sql = "SELECT * FROM menu_items WHERE menu_section = ?";
        List<IMenuItem> menuItems = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, section.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(rs.getLong("id"));
                    menuItem.setName(rs.getString("name"));
                    menuItem.setMenuSection(MenuSection.valueOf(rs.getString("menu_section")));
                    menuItem.setPrice(rs.getBigDecimal("price"));

                    menuItems.add(menuItem);
                }
            }

            return menuItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        if (menuItem==null) throw new IllegalArgumentException("Menu Item cannot be null");
        String sql = "DELETE FROM menu_items WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1, menuItem.getId());

            int affectedRow= ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to delete menuItem, no rows affected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql="DELETE FROM menu_items";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            int affectedRow= ps.executeUpdate();
//            if (affectedRow == 0) throw new SQLException("Failed to delete menuItem, no rows affected");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
