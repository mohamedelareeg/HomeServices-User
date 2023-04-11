package com.rovaindu.homeservice.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.utils.SparseBooleanArrayParcelable;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.util.ArrayList;
import java.util.List;

public class OrderCartAdapter extends RecyclerView.Adapter<OrderCartAdapter. AgentViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesAgent selectedAgent;
    private List<Service> pendingAgentServiesList;
    Context context;
    private ServiesAdapterListener listener;
    public final SparseBooleanArrayParcelable selectedItems = new SparseBooleanArrayParcelable();
    private int currentSelectedPos;

    public OrderCartAdapter(Context context, ServiesAgent selectedAgent ,  List<Service> pendingAgentServiesList) {
        this.context = context;
        this.selectedAgent = selectedAgent;
        this.pendingAgentServiesList = pendingAgentServiesList;
    }

    public void setListener(ServiesAdapterListener listener) {
        this.listener = listener;
    }

    public List<Service> getAgentsList() {
        return pendingAgentServiesList;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_cart_layout_item, parent, false);

        return new  AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AgentViewHolder holder, final int position) {
        final Service agents = pendingAgentServiesList.get(position);
        final ServiesAgent agent = selectedAgent;

        holder.serviesName.setText(agents.getName());
        holder.serviesDesc.setText(agents.getName());
        holder.bind(agents);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItems.size() > 0 && listener != null)
                    listener.onItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null)
                    listener.onItemLongClick(position);
                return true;
            }
        });

        if (currentSelectedPos == position) currentSelectedPos = -1;

        //holder.setCategoryImage(categories.getImage());


    }

    @Override
    public int getItemCount() {
        return pendingAgentServiesList.size();
    }

    public void deleteEmails() {
        List<Service> agents = new ArrayList<>();
        for (Service agent : this.pendingAgentServiesList) {
            if (agent.isSelected())
                agents.add(agent);
        }

        this.pendingAgentServiesList.removeAll(agents);
        notifyDataSetChanged();
        currentSelectedPos = -1;
    }

    public void toggleSelection(int position) {
        currentSelectedPos = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            pendingAgentServiesList.get(position).setSelected(false);
        } else {
            selectedItems.put(position, true);
            pendingAgentServiesList.get(position).setSelected(true);
        }
        notifyItemChanged(position);
    }
    class AgentViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout AgentPanel;
        private TextViewAr serviesName , serviesDesc , agentSelectNotify;
        private RadioButton agentSelected;

        private  AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            AgentPanel = itemView.findViewById(R.id.AgentPanel);
            serviesName = itemView.findViewById(R.id.serviesName);
            serviesDesc = itemView.findViewById(R.id.serviesDesc);
            agentSelectNotify = itemView.findViewById(R.id.agentSelectNotify);
            agentSelected = itemView.findViewById(R.id.agentSelected);

        }


        void bind(Service agents)
        {
            if (agents.isSelected()) {
                /*
                agentSelectNotify.setBackground(oval(Color.rgb(26, 115, 233), agentSelectNotify));
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.rgb(232, 240, 253));
                itemView.setBackground(gradientDrawable);

                 */
                agentSelectNotify.setVisibility(View.VISIBLE);
                agentSelected.setChecked(true);
            } else {
                /*
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                gradientDrawable.setCornerRadius(32f);
                gradientDrawable.setColor(Color.WHITE);
                itemView.setBackground(gradientDrawable);

                 */
                agentSelectNotify.setVisibility(View.GONE);
                agentSelected.setChecked(false);
            }

            // animation
            if (selectedItems.size() > 0)
                animate(agentSelectNotify, agents);
        }
        private void animate(final TextView view, final Service agents) {
            ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
            final ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
            oa1.setInterpolator(new DecelerateInterpolator());
            oa2.setInterpolator(new AccelerateDecelerateInterpolator());
            oa1.setDuration(200);
            oa2.setDuration(200);

            oa1.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (agents.isSelected())
                        view.setText("\u2713");
                    oa2.start();
                }
            });
            oa1.start();
        }

    }
    public interface ServiesAdapterListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }

    private static ShapeDrawable oval(@ColorInt int color, View view) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.setIntrinsicHeight(view.getHeight());
        shapeDrawable.setIntrinsicWidth(view.getWidth());
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

}
