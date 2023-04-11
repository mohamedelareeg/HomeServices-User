package com.rovaindu.homeservice.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.controller.SettingsActivity;
import com.rovaindu.homeservice.interfaces.RecyclerViewClickListener;
import com.rovaindu.homeservice.model.Theme;
import com.rovaindu.homeservice.utils.views.ThemeView;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.MyViewHolder> {
 
    private List<Theme> themeList;
    private RecyclerViewClickListener mRecyclerViewClickListener;
 
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ThemeView themeView;
        private RecyclerViewClickListener mListener;
 
        public MyViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            mListener = listener;
            themeView = (ThemeView) view.findViewById(R.id.themeView);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
            SettingsActivity.selectedTheme = getAdapterPosition();
            SettingsActivity.mTheme = SettingsActivity.mThemeList.get(getAdapterPosition()).getId();
            themeView.setActivated(true);
            ThemeAdapter.this.notifyDataSetChanged();
        }
    }
 
 
    public ThemeAdapter(List<Theme> themeList, RecyclerViewClickListener recyclerViewClickListener) {
        this.themeList = themeList;
        mRecyclerViewClickListener = recyclerViewClickListener;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_theme, parent, false);
 
        return new MyViewHolder(itemView, mRecyclerViewClickListener);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Theme theme = themeList.get(position);
        holder.themeView.setTheme(theme);

        if(SettingsActivity.selectedTheme == position){
            holder.themeView.setActivated(true);
        }else {
            holder.themeView.setActivated(false);
        }
    }
 
    @Override
    public int getItemCount() {
        return themeList.size();
    }
}