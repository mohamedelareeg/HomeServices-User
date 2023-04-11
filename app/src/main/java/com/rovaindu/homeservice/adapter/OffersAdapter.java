package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.model.Offer;

import java.util.ArrayList;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.CategoryViewHolder> {
    private ArrayList<Offer> categoriesList;
    Context context;

    public OffersAdapter(Context context, ArrayList<Offer> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_layout_item, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Offer offer = categoriesList.get(position);

        holder.offerImage.setImageResource(offer.getImage());
       // holder.setCategoryImage(offers.getImage());


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView offerImage;


        private CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            offerImage = itemView.findViewById(R.id.offerImage);

        }

        private void setCategoryImage(String url) {
            Glide.with(context).load(url).into(offerImage);

        }

    }

}
