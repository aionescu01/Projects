package com.example.proiect_dsdm.budget.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect_dsdm.R;
import com.google.android.material.card.MaterialCardView;

public class CategoryAdapter extends ListAdapter<Category, CategoryAdapter.CategoryViewHolder> {
    private OnCategoryClickListener clickListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
        void onDeleteClick(Category category);
    }

    public CategoryAdapter() {
        super(new CategoryDiffCallback());
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);
        holder.bind(category, clickListener);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameText;
        private final ImageButton deleteButton;
        private final MaterialCardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (MaterialCardView) itemView;
            nameText = itemView.findViewById(R.id.category_name);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }

        public void bind(Category category, OnCategoryClickListener listener) {
            nameText.setText(category.getName());

            if (listener != null) {
                cardView.setOnClickListener(v -> listener.onCategoryClick(category));
                deleteButton.setOnClickListener(v -> listener.onDeleteClick(category));
            }
        }
    }

    static class CategoryDiffCallback extends DiffUtil.ItemCallback<Category> {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}