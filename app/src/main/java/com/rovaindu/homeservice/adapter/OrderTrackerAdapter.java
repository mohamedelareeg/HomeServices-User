package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.util.List;

public class OrderTrackerAdapter extends RecyclerView.Adapter<OrderTrackerAdapter. AgentViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesOrder selectedOrder;
    private List<Service> agentServiesList;
    Context context;


    public OrderTrackerAdapter(Context context, ServiesOrder selectedOrder , List<Service> serviceList ) {
        this.context = context;
        this.selectedOrder = selectedOrder;
        this.agentServiesList = serviceList;//selectedOrder.getAgent().getAgentServies()
    }



    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.track_order_layout_item, parent, false);

        return new  AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AgentViewHolder holder, final int position) {
        final Service agents = agentServiesList.get(position);
        final ServiesOrder order = selectedOrder;

        holder.agentName.setText(agents.getName());
        holder.agentPrice.setText(context.getResources().getString(R.string.cost_per_hour) + ": " + agents.getName() + " " + context.getResources().getString(R.string.currency));
        holder.setAgentImage(order.getAgent().getImage());
        float rating = 3.5f;
        //float rating = agents.getRate() / agents.getRatecount();
        holder.agentRatingbar.setRating(rating);
        holder.agentRating.setText("( " +context.getResources().getString(R.string.raring) + " " + rating + " )");
        holder.agentPayment.setText(context.getResources().getString(R.string.cash));


        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return agentServiesList.size();
    }


    class AgentViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private ImageView agentImg;
        private TextViewAr agentName , agentSelectNotify , agentRating , agentPrice , agentPayment;
        private RatingBar agentRatingbar;


        private  AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            agentImg = itemView.findViewById(R.id.agentImg);
            agentName = itemView.findViewById(R.id.agentName);
            agentSelectNotify = itemView.findViewById(R.id.agentSelectNotify);
            agentRating = itemView.findViewById(R.id.agentRating);
            agentPrice = itemView.findViewById(R.id.agentPrice);
            agentPayment = itemView.findViewById(R.id.agentPayment);
            agentRatingbar = itemView.findViewById(R.id.agentRatingbar);

        }

        private void setAgentImage(String url) {
            Glide.with(context).load(url).into(agentImg);

        }

    }


}
