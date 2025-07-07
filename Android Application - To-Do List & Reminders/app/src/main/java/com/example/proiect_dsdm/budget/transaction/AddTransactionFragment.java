package com.example.proiect_dsdm.budget.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proiect_dsdm.R;
import com.example.proiect_dsdm.budget.category.Category;

import java.util.Date;

public class AddTransactionFragment extends Fragment {
    private AddTransactionViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddTransactionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);

        Button addButton = view.findViewById(R.id.add_transaction_button);
        addButton.setOnClickListener(v -> {
            Transaction newTransaction = createTransactionFromUI();
            if (newTransaction != null) {
                viewModel.addTransaction(newTransaction);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner categorySpinner = view.findViewById(R.id.category_spinner);
        viewModel.getUserCategories().observe(getViewLifecycleOwner(), categories -> {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
        });
    }

    private Transaction createTransactionFromUI() {
        EditText descriptionInput = getView().findViewById(R.id.description_input);
        EditText amountInput = getView().findViewById(R.id.amount_input);
        Spinner categorySpinner = getView().findViewById(R.id.category_spinner);
        EditText commentInput = getView().findViewById(R.id.comment_input);
        RadioGroup typeRadioGroup = getView().findViewById(R.id.type_radio_group);

        // Create a new Transaction object
        Transaction transaction = new Transaction();

        // Set the description
        transaction.setDescription(descriptionInput.getText().toString());

        // Set the amount
        try {
            double amount = Double.parseDouble(amountInput.getText().toString());
            transaction.setAmount(amount);
        } catch (NumberFormatException e) {
            // Handle invalid input
            Toast.makeText(getContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Set the category
        Category selectedCategory = (Category) categorySpinner.getSelectedItem();
        transaction.setCategoryId(selectedCategory.getId());

        // Set the comment
        transaction.setComment(commentInput.getText().toString());

        // Set the transaction type
        int selectedTypeId = typeRadioGroup.getCheckedRadioButtonId();
        if (selectedTypeId == R.id.radio_expense) {
            transaction.setType(TransactionType.EXPENSE);
        } else if (selectedTypeId == R.id.radio_income) {
            transaction.setType(TransactionType.INCOME);
        } else {
            // Handle case where no type is selected
            Toast.makeText(getContext(), "Please select a transaction type", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Set the date (assuming you want to use the current date)
        transaction.setDate(System.currentTimeMillis());

        return transaction;
    }
}