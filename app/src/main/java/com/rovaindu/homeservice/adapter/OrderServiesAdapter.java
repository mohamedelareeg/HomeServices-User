package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.controller.agents.AgentsServiesActivity;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;
import java.util.List;

public class OrderServiesAdapter extends RecyclerView.Adapter<OrderServiesAdapter. AgentDatePickerViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesAgent selectedAgent;
    private List<Service> pendingAgentServiesList;
    Context context;

    public OrderServiesAdapter(Context context, ServiesAgent selectedAgent , List<Service> pendingAgentServiesList) {
        this.context = context;
        this.selectedAgent = selectedAgent;
        this.pendingAgentServiesList = pendingAgentServiesList;
    }

    @NonNull
    @Override
    public AgentDatePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_servies_layout_item, parent, false);

        return new  AgentDatePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentDatePickerViewHolder holder, int position) {
        final Service agents = pendingAgentServiesList.get(position);
        final ServiesAgent agent = selectedAgent;
        holder.serviesName.setText(agents.getName());
        holder.serviesDesc.setText(agents.getName());
        holder.AgentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AgentsServiesActivity.class);
                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) selectedAgent);
                context.startActivity(i);

            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return pendingAgentServiesList.size();
    }

    class AgentDatePickerViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout AgentPanel;
        private TextViewAr serviesName , serviesDesc;

        private  AgentDatePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            serviesName = itemView.findViewById(R.id.serviesName);
            serviesDesc = itemView.findViewById(R.id.serviesDesc);

        }



    }

}
