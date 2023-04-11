package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.rovaindu.homeservice.R;

import com.rovaindu.homeservice.controller.orders.OrderDetailsActivity;
import com.rovaindu.homeservice.model.AgentAvaliability;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.AutoFitTextView;

import java.io.Serializable;
import java.util.List;

public class AgentDatePickerAdapter extends RecyclerView.Adapter<AgentDatePickerAdapter. AgentDatePickerViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesCategory selectedCategory;
    private ServiesAgent selectedAgent;
    private AgentAvaliability agentAvaliability;
    private List<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;
    Context context;

    public AgentDatePickerAdapter(Context context, ServiesAgent selectedAgent , AgentAvaliability agentAvaliability , List<AgentAvaliability.DayAvaliabilty> dayAvaliabilties , ServiesCategory selectedCategory) {
        this.context = context;
        this.selectedAgent = selectedAgent;
        this.agentAvaliability = agentAvaliability;
        this.dayAvaliabilties = dayAvaliabilties;
        this.selectedCategory = selectedCategory;
    }

    @NonNull
    @Override
    public AgentDatePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_date_picker_layout_item, parent, false);

        return new  AgentDatePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentDatePickerViewHolder holder, int position) {
        final AgentAvaliability.DayAvaliabilty agents = dayAvaliabilties.get(position);
        final AgentAvaliability selectDay = agentAvaliability;
        final ServiesAgent agent = selectedAgent;
        final ServiesCategory category = selectedCategory;
        holder.timePicker.setText(agents.getDate());
        if(agents.getStatue() == 2)
        {
            holder.timePicker.setEnabled(false);
        }
        holder.AgentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OrderDetailsActivity.class);
                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);
                i.putExtra(Constants.BUNDLE_SELECTED_DAY, (Serializable) selectDay);
                i.putExtra(Constants.BUNDLE_SELECTED_DATE, (Serializable) agents);
                i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                context.startActivity(i);

            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return dayAvaliabilties.size();
    }

    class AgentDatePickerViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private AutoFitTextView timePicker;

        private  AgentDatePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            timePicker = itemView.findViewById(R.id.timePicker);

        }



    }

}
