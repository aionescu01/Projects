package com.example.proiect_dsdm.budget.category;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proiect_dsdm.budget.BudgetRepository;

import java.util.List;

import javax.inject.Inject;

public class CategoryViewModel extends ViewModel {
    private final BudgetRepository repository;
    private final MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public CategoryViewModel(BudgetRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadCategories(String userId) {
        viewModelScope.launch {
            try {
                List<Category> categoryList = repository.getCategoriesByUser(userId);
                categories.setValue(categoryList);
            } catch (Exception e) {
                error.setValue("Failed to load categories: " + e.getMessage());
            }
        }
    }

    public void createCategory(String name, String userId) {
        viewModelScope.launch {
            try {
                Category category = new Category(null, name, userId);
                repository.createCategory(category);
                loadCategories(userId);
            } catch (Exception e) {
                error.setValue("Failed to create category: " + e.getMessage());
            }
        }
    }

    public void deleteCategory(Category category) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(category.getId());
                loadCategories(category.getUserId());
            } catch (Exception e) {
                error.setValue("Failed to delete category: " + e.getMessage());
            }
        }
    }
}
