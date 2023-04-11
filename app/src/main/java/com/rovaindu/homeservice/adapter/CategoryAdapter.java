package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.controller.servies.InprogressActivity;
import com.rovaindu.homeservice.controller.servies.ServiesSubCategoriesActivity;
import com.rovaindu.homeservice.model.CategoryOld;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ArrayList<ServiesCategory> categoryList;
    Context context;

    public CategoryAdapter(Context context, ArrayList<ServiesCategory> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout_item, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final ServiesCategory category = categoryList.get(position);

        holder.categoryTitle.setText(category.getName());
        holder.setCategoryImage(category.getImage());
        //holder.categoryImage.setImageResource(category.getImage());
        holder.CategoryPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DefaultCategory = 1
                //InnerSubCategory = 2
                Intent i = new Intent(context, ServiesSubCategoriesActivity.class);
                i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                context.startActivity(i);
            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView CategoryPanel;
        private ImageView categoryImage;
        private TextView categoryTitle;

        private CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            CategoryPanel = itemView.findViewById(R.id.CategoryPanel);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryTitle = itemView.findViewById(R.id.category_title);
        }

        private void setCategoryImage(String url) {
            Glide.with(context).load(url).into(categoryImage);

        }

    }

}
