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
import com.rovaindu.homeservice.controller.chat.ChatActivity;
import com.rovaindu.homeservice.controller.orders.TrackActivity;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.chat.ChatConstants;
import com.rovaindu.homeservice.utils.views.chat.StringContract;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter. AgentDatePickerViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private List<ServiesOrder> orderList;
    Context context;

    public OrdersAdapter(Context context, List<ServiesOrder> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public AgentDatePickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout_item, parent, false);

        return new  AgentDatePickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentDatePickerViewHolder holder, int position) {
        final ServiesOrder order = orderList.get(position);
        holder.orderName.setText(order.getServices().get(0).getName());
        holder.orderStatus.setText(context.getResources().getString(R.string.pending));//order.getStatus()

        holder.orderDate.setText(order.getDay() + " | " + order.getHour());
        holder.btnCallAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(Constants.BUNDLE_ORDER_LIST , (Serializable)order);
                intent.putExtra(StringContract.IntentStrings.NAME,order.getAgent().getName());
                intent.putExtra(StringContract.IntentStrings.AVATAR,order.getAgent().getImage());
                intent.putExtra(StringContract.IntentStrings.UID,order.getAgent().getId());
                intent.putExtra(StringContract.IntentStrings.PARENT_ID,0);
                intent.putExtra(StringContract.IntentStrings.MESSAGE_TYPE, ChatConstants.MESSAGE_TYPE_TEXT);
                intent.putExtra(StringContract.IntentStrings.SENTAT,order.getDay());
                intent.putExtra(StringContract.IntentStrings.TEXTMESSAGE,"");
                intent.putExtra(StringContract.IntentStrings.TYPE,"user");
                intent.putExtra(StringContract.IntentStrings.UID,order.getAgent().getId());
                context.startActivity(intent);
            }
        });
        holder.AgentPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TrackActivity.class);
                i.putExtra(Constants.BUNDLE_ORDER_LIST, (Serializable) order);
                context.startActivity(i);

            }
        });

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class AgentDatePickerViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private TextViewAr orderName , orderDate , orderStatus;
        private TextViewAr btnCallAgent, btnCallApp;

        private  AgentDatePickerViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            orderName = itemView.findViewById(R.id.orderName);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            btnCallAgent = itemView.findViewById(R.id.btnCallAgent);
            btnCallApp = itemView.findViewById(R.id.btnCallApp);

        }
        private String getDate(long time) {
            Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy "); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"));

            return sdf.format(date);
        }


    }


}
