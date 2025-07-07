package com.example.proiect_dsdm.budget.transaction;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proiect_dsdm.budget.BudgetRepository;
import com.example.proiect_dsdm.budget.category.Category;
import com.example.proiect_dsdm.budget.transaction.Transaction;

import java.util.List;

public class AddTransactionViewModel extends ViewModel {
    private BudgetRepository repository;
    private LiveData<List<Category>> userCategories;

    public AddTransactionViewModel(Application application){
        repository = new BudgetRepository(application);
        userCategories = repository.getUserCategories();
    }

    public void addTransaction(Transaction transaction) {
        repository.insertTransaction(transaction);
    }
    public LiveData<List<Category>> getUserCategories() {
        return userCategories;
    }
}
