package com.example.proiect_dsdm.budget.category;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryDao {
    private Connection connection;

    public CategoryDao(Connection connection) {
        this.connection = connection;
    }

    public String createCategory(Category category) {
        if (getCategoryByName(category.getName(), category.getUserId()) != null) {
            throw new IllegalArgumentException("Category already exists: " + category.getName());
        }

        String sql = "INSERT INTO categories (id, name, user_id) VALUES (?, ?, ?)";
        String categoryId = UUID.randomUUID().toString();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            stmt.setString(2, category.getName());
            stmt.setString(3, category.getUserId());

            stmt.executeUpdate();
            return categoryId;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating category", e);
        }
    }

    public String getCategoryByName(String name, String userId) {
        String sql = "SELECT id FROM categories WHERE name = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching category", e);
        }
        return null;
    }

    public List<Category> getCategoriesByUser(String userId) {
        String sql = "SELECT * FROM categories WHERE user_id = ? ORDER BY name";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setUserId(rs.getString("user_id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching categories", e);
        }
        return categories;
    }

    public void deleteCategory(String categoryId) {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category", e);
        }
    }
}
