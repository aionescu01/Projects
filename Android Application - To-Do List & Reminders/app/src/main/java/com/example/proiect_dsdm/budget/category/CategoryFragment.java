package com.example.proiect_dsdm.budget.category;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect_dsdm.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class CategoryFragment extends Fragment {
    private CategoryViewModel viewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        auth = FirebaseAuth.getInstance();

        setupRecyclerView(view);
        setupObservers();
        setupAddButton(view);

        String userId = auth.getCurrentUser().getUid();
        viewModel.loadCategories(userId);
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.category_recycler_view);
        adapter = new CategoryAdapter();

        adapter.setOnCategoryClickListener(new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                // Navigate to transactions for this category
                Bundle args = new Bundle();
                args.putString("categoryId", category.getId());
                Navigation.findNavController(view)
                        .navigate(R.id.action_categoryFragment_to_transactionListFragment, args);
            }

            @Override
            public void onDeleteClick(Category category) {
                showDeleteConfirmationDialog(category);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void setupObservers() {
        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            adapter.submitList(categories);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupAddButton(View view) {
        FloatingActionButton addButton = view.findViewById(R.id.add_category_button);
        addButton.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void showAddCategoryDialog() {
        EditText input = new EditText(requireContext());
        input.setHint("Category Name");

        new AlertDialog.Builder(requireContext())
                .setTitle("Add Category")
                .setView(input)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        viewModel.createCategory(name, auth.getCurrentUser().getUid());
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmationDialog(Category category) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Category")
                .setMessage("Are you sure you want to delete " + category.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    viewModel.deleteCategory(category);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
