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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.utils.SparseBooleanArrayParcelable;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.util.ArrayList;
import java.util.List;

public class ServiesProviderAdapter extends RecyclerView.Adapter<ServiesProviderAdapter. AgentViewHolder> {
    //DefaultCategory = 1
    //InnerSubCategory = 2
    private ServiesAgent selectedAgent;
    private List<Service> agentsList;
    Context context;
    private ServiesAdapterListener listener;
    public final SparseBooleanArrayParcelable selectedItems = new SparseBooleanArrayParcelable();
    private int currentSelectedPos;

    public ServiesProviderAdapter(Context context, ServiesAgent selectedAgent) {
        this.context = context;
        this.selectedAgent = selectedAgent;
        this.agentsList = selectedAgent.getServices();
    }

    public void setListener(ServiesAdapterListener listener) {
        this.listener = listener;
    }

    public List<Service> getAgentsList() {
        return agentsList;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_servies_layout_item, parent, false);

        return new  AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AgentViewHolder holder, final int position) {
        final Service agents = agentsList.get(position);
        final ServiesAgent agent = selectedAgent;

        holder.agentName.setText(agents.getName());
        holder.agentPrice.setText(context.getResources().getString(R.string.cost_per_hour) + ": " + 100 + " " + context.getResources().getString(R.string.currency));//TODo
        holder.setAgentImage(agent.getImage());
        //float rating = 3.5f;
        float rating = 0f;
        if(agent.getRatesCount() > 0) {
            rating = (float) (agent.getRate() / agent.getRatesCount());
        }
        holder.agentRatingbar.setRating(rating);
        holder.agentRating.setText("( " +context.getResources().getString(R.string.raring) + " " + rating + " )");
        holder.agentPayment.setText(context.getResources().getString(R.string.cash));
        holder.bind(agents);
       // holder.agentSelected.setEnabled(false);
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
        return agentsList.size();
    }

    public void deleteEmails() {
        List<Service> agents = new ArrayList<>();
        for (Service agent : this.agentsList) {
            if (agent.isSelected())
                agents.add(agent);
        }

        this.agentsList.removeAll(agents);
        notifyDataSetChanged();
        currentSelectedPos = -1;
    }

    public void toggleSelection(int position) {
        currentSelectedPos = position;
        if (selectedItems.get(position)) {
            selectedItems.delete(position);
            agentsList.get(position).setSelected(false);
        } else {
            selectedItems.put(position, true);
            agentsList.get(position).setSelected(true);
        }
        notifyItemChanged(position);
    }
    class AgentViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView AgentPanel;
        private ImageView agentImg;
        private TextViewAr agentName , agentSelectNotify , agentRating , agentPrice , agentPayment;
        private RatingBar agentRatingbar;
        private RadioButton agentSelected;

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
            agentSelected = itemView.findViewById(R.id.agentSelected);
        }

        private void setAgentImage(String url) {
            Glide.with(context).load(url).into(agentImg);

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
