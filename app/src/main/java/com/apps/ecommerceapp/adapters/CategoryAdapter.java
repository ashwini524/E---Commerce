package com.apps.ecommerceapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.ecommerceapp.R;
import com.apps.ecommerceapp.databinding.ItemCategoriesBinding;
import com.apps.ecommerceapp.model.Category;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {
    Context context;
    ArrayList<Category> categories;
    public  CategoryAdapter(Context context, ArrayList<Category> categories){
        this.context= context;
        this.categories= categories;
    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewholder(LayoutInflater.from(context).inflate(R.layout.item_categories, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        Category category = categories.get(position);
        holder.binding.label.setText(category.getName());
        Glide.with(context)
                .load(category.getIcon())
                .into(holder.binding.image);

        //images come in string format from server
        //colors come in string format from server
        holder.binding.image.setBackgroundColor(Color.parseColor(category.getColor()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder {
        ItemCategoriesBinding binding;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            binding= ItemCategoriesBinding.bind(itemView);
        }
    }

}
