package com.example.proiect_dsdm.budget;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proiect_dsdm.budget.category.Category;
import com.example.proiect_dsdm.budget.category.CategoryDao;
import com.example.proiect_dsdm.budget.transaction.Transaction;
import com.example.proiect_dsdm.budget.transaction.TransactionDao;
import com.example.proiect_dsdm.budget.transaction.TransactionType;
import com.example.proiect_dsdm.task.AppDatabase;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BudgetRepository {
    private final TransactionDao transactionDao;
    private final CategoryDao categoryDao;
    private final Connection connection;
    private final String currentUserId;
    private final ExecutorService executorService;

    public BudgetRepository(Connection connection) {
        this.connection = connection;
        transactionDao = new TransactionDao(connection);
        categoryDao = new CategoryDao(connection);
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        executorService = Executors.newSingleThreadExecutor();
    }

    public String createCategory(Category category) {
        return categoryDao.createCategory(category);
    }

    public List<Category> getCategoriesByUser(String userId) {
        return categoryDao.getCategoriesByUser(userId);
    }

    public void deleteCategory(String categoryId) {
        categoryDao.deleteCategory(categoryId);
    }

    public boolean addTransactionWithCategory(Transaction transaction, String categoryName) {
        try {
            connection.setAutoCommit(false);

            // Check if category exists
            String categoryId = categoryDao.getCategoryByName(categoryName, transaction.getUserId());
            if (categoryId == null) {
                throw new IllegalArgumentException("Category does not exist: " + categoryName);
            }

            // Set category ID and add transaction
            transaction.setCategoryId(categoryId);
            long transactionId = transactionDao.addTransaction(transaction);

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error rolling back transaction", rollbackEx);
            }
            throw new RuntimeException("Error in transaction", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Error resetting auto-commit", e);
            }
        }
    }
}



}
