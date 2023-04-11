package com.rovaindu.homeservice.controller.agents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.ServiesProviderAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.controller.orders.OrderDetailsActivity;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.shimmer.ShimmerFrameLayout;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class AgentsServiesActivity extends BaseActivity {

    private static final String TAG = "AgentsServiesActivity";
    private Toolbar toolbar;
    private ServiesAgent agent;
    private ServiesProviderAdapter agentAdapter;
    private RecyclerView recAgent;

    private int userPage = 1;

    private ShimmerFrameLayout shimmerFrameLayout;

    private ActionMode actionMode;
    private TextViewAr btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_servies);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.servies_provider_choose));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentsServiesActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);

        shimmerFrameLayout = findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        recAgent = findViewById(R.id.recAgents);
        btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(agentAdapter.selectedItems.size() > 0) {
                    Intent i = new Intent(AgentsServiesActivity.this, OrderDetailsActivity.class);
                    i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);

                    startActivity(i);
                    Log.d(TAG, "onClick: " + agentAdapter.selectedItems.toString());
                }
            }
        });

        AssignAgentsList();

        if(agent !=null)
        {
            getAgentsData(userPage);
        }

    }

    private void AssignAgentsList(){


        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 1);

        recAgent.setLayoutManager(mLayoutManager);
        recAgent.setItemAnimator(new DefaultItemAnimator());
        recAgent.setHasFixedSize(true);
        recAgent.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        agentAdapter = new ServiesProviderAdapter(this , agent);
        recAgent.setAdapter(agentAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(
                // below statement: used at move and sort
                // new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                //ItemTouchHelper.LEFT)
                new ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(recAgent);

        agentAdapter.setListener(new ServiesProviderAdapter.ServiesAdapterListener() {
            @Override
            public void onItemClick(int position) {
                enableActionMode(position);

            }

            @Override
            public void onItemLongClick(int position) {
                enableActionMode(position);
            }
        });
    }

    private void getAgentsData(int userPage) {

        agentAdapter.notifyDataSetChanged();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();


    }

    private void enableActionMode(int position) {

        if (actionMode == null)
            actionMode = startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    toolbar.setVisibility(View.GONE);
                    mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    if (item.getItemId() == R.id.action_delete) {
                        agentAdapter.deleteEmails();
                        mode.finish();
                        return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    agentAdapter.selectedItems.clear();
                    List<Service> agents = agentAdapter.getAgentsList();
                    for (Service agent : agents) {
                        if (agent.isSelected())
                            agent.setSelected(false);
                    }

                    agentAdapter.notifyDataSetChanged();
                    actionMode = null;
                    toolbar.setVisibility(View.VISIBLE);
                }
            });

        agentAdapter.toggleSelection(position);
        final int size = agentAdapter.selectedItems.size();
        if (size == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(size + "");
            actionMode.invalidate();
        }



    }

    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            Collections.swap(agentAdapter.getAgentsList(), from, to);
            agentAdapter.notifyItemMoved(from, to);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            agentAdapter.getAgentsList().remove(viewHolder.getAdapterPosition());
            agentAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    }
}