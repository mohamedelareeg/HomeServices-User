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
import com.rovaindu.homeservice.controller.ComplainActivity;
import com.rovaindu.homeservice.retrofit.models.Complain;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ComplainsAdapter extends RecyclerView.Adapter<ComplainsAdapter. AgentDatePickerViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private List<Complain> complainList;
    Context context;

    public ComplainsAdapter(Context context, List<Complain> complainList) {
        this.context = context;
        this.complainList = complainList;
    }

    @NonNull
    @Override
    public AgentDatePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.complain_layout_item, parent, false);

        return new  AgentDatePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentDatePickerViewHolder holder, int position) {
        final Complain complain = complainList.get(position);
        holder.orderName.setText(complain.getTitle());
        holder.orderStatus.setText(complain.getBody());

        holder.orderDate.setText(complain.getUpdatedAt());
        holder.AgentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComplainActivity.class);
                i.putExtra(Constants.BUNDLE_COMPLAIN_LIST, (Serializable) complain);
                context.startActivity(i);

            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return complainList.size();
    }

    class AgentDatePickerViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private TextViewAr orderName , orderDate , orderStatus;

        private  AgentDatePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            orderName = itemView.findViewById(R.id.orderName);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderStatus = itemView.findViewById(R.id.orderStatus);

        }
        private String getDate(long time) {
            Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy "); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

            return sdf.format(date);
        }


    }


}
