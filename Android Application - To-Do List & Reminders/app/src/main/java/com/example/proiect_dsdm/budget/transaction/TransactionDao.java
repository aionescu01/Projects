package com.example.proiect_dsdm.budget.transaction;

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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {
    private Connection connection;  // Azure SQL connection

    public TransactionDao(Connection connection) {
        this.connection = connection;
    }

    public long addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (amount, description, category_id, timestamp, user_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, transaction.getAmount());
            stmt.setString(2, transaction.getDescription());
            stmt.setString(3, transaction.getCategoryId());
            stmt.setLong(4, transaction.getTimestamp());
            stmt.setString(5, transaction.getUserId());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error adding transaction", e);
        }
        return -1;
    }

    public List<Transaction> getTransactionsByUser(String userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY timestamp DESC";
        List<Transaction> transactions = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                // Set transaction properties from ResultSet
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching transactions", e);
        }
        return transactions;
    }

    public double getTotalSpentByCategory(String categoryId, String userId) {
        String sql = "SELECT SUM(amount) FROM transactions WHERE category_id = ? AND user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryId);
            stmt.setString(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error calculating total spent", e);
        }
        return 0.0;
    }

}
