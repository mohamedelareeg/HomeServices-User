package com.rovaindu.homeservice.controller.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.OrderTrackerAdapter;
import com.rovaindu.homeservice.adapter.TimeLineAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.model.TimeLineModel;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.timeline.OrderStatus;
import com.rovaindu.homeservice.utils.timeline.Orientation;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.util.ArrayList;
import java.util.List;


public class TrackActivity extends BaseActivity  {


    private static final String TAG = "TrackActivity";
    private OrderTrackerAdapter agentAdapter;
    private RecyclerView recAgent;
    private StaggeredGridLayoutManager mLayoutManager;
    private Toolbar toolbar;
    private TimeLineAdapter mTimeLineAdapter;
    private RecyclerView mRecyclerView;
    private List<TimeLineModel> mDataList = new ArrayList<>();
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    List<Service> serviceList;

    private int userPage = 1;
    private ServiesOrder order;
    private TextView total_amount , id_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.order_details));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackActivity.this, SearchActivity.class);
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

        order = (ServiesOrder) getIntent().getExtras().getSerializable(Constants.BUNDLE_ORDER_LIST);
        recAgent = findViewById(R.id.shoppingCartRecycleView);
        mOrientation = (Orientation) getIntent().getSerializableExtra(HomeActivity.EXTRA_ORIENTATION);
        mWithLinePadding = getIntent().getBooleanExtra(HomeActivity.EXTRA_WITH_LINE_PADDING, false);


        total_amount = findViewById(R.id.total_amount);
        id_txt = findViewById(R.id.id_txt);
        total_amount.setText("100");
        id_txt.setText(String.valueOf(order.getId()));

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView();
        serviceList = new ArrayList<>();
        AssignAgentsList();

        getAgentsData(userPage);

    }

    private void AssignAgentsList(){


        mLayoutManager = new StaggeredGridLayoutManager(1 , LinearLayoutManager.HORIZONTAL);
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

        agentAdapter = new OrderTrackerAdapter(this , order , serviceList);
        recAgent.setAdapter(agentAdapter);


    }

    private void getAgentsData(int userPage) {

        serviceList.addAll(order.getServices());
        agentAdapter.notifyDataSetChanged();



    }
    private void initView() {

        //setDataListItems();
        loadStatus();
        mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation, mWithLinePadding);
        mRecyclerView.setAdapter(mTimeLineAdapter);

    }
    private void loadStatus() {
        String pendingMessege = getResources().getString(R.string.pending_1);
        String confirmedMessage = getResources().getString(R.string.confirmed_1)  + getResources().getString(R.string.customer_1); // + " ( " + order.getUser().getCity().getName() + " , " + order.getUser().getCountry() + " ) "
        String PackedMessage = getResources().getString(R.string.packed_1) + " " + order.getUser().getPhone();
        String DeliverdMessege = getResources().getString(R.string.deliverd_1);
        String FinishedMessege = getResources().getString(R.string.finished_1) + " , " + getResources().getString(R.string.finished_2);
        String CancelMessege = getResources().getString(R.string.order_rejected);
        switch (order.getStatus())
        {
            case 0:
            {

                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.ACTIVE));

                break;
            }
            case 1:
            {

                mDataList.add(new TimeLineModel(confirmedMessage, "2017-02-10 14:30", OrderStatus.ACTIVE));
                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.COMPLETED));

                break;
            }
            case 2:
            {

                mDataList.add(new TimeLineModel(PackedMessage, "2017-02-10 15:00", OrderStatus.ACTIVE));
                mDataList.add(new TimeLineModel(confirmedMessage, "2017-02-10 14:30", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.COMPLETED));

                break;
            }
            case 3:
            {
                mDataList.add(new TimeLineModel(DeliverdMessege, "2017-02-11 08:00", OrderStatus.ACTIVE));
                mDataList.add(new TimeLineModel(PackedMessage, "2017-02-10 15:00", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(confirmedMessage, "2017-02-10 14:30", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.COMPLETED));

                break;
            }

            case 4:
            {
                mDataList.add(new TimeLineModel(FinishedMessege, "2017-02-11 08:00", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(DeliverdMessege, "2017-02-11 08:00", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(PackedMessage, "2017-02-10 15:00", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(confirmedMessage, "2017-02-10 14:30", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.COMPLETED));

                break;
            }


            case 5:
            {

                mDataList.add(new TimeLineModel(CancelMessege, "2017-02-10 14:00", OrderStatus.COMPLETED));

                break;
            }
            default:
            {
                mDataList.add(new TimeLineModel(FinishedMessege, "2017-02-11 08:00", OrderStatus.COMPLETED));
                mDataList.add(new TimeLineModel(DeliverdMessege, "2017-02-11 08:00", OrderStatus.INACTIVE));
                mDataList.add(new TimeLineModel(PackedMessage, "2017-02-10 15:00", OrderStatus.INACTIVE));
                mDataList.add(new TimeLineModel(confirmedMessage, "2017-02-10 14:30", OrderStatus.INACTIVE));
                mDataList.add(new TimeLineModel(pendingMessege, "2017-02-10 14:00", OrderStatus.ACTIVE));

            }
        }



    }

    private void setDataListItems(){

        mDataList.add(new TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE));
        mDataList.add(new TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE));
        mDataList.add(new TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED));
        mDataList.add(new TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED));




    }
    private LinearLayoutManager getLinearLayoutManager() {
        if (mOrientation == Orientation.HORIZONTAL) {
            return new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Menu
        switch (item.getItemId()) {
            //When home is clicked
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if(mOrientation!=null)
            savedInstanceState.putSerializable(HomeActivity.EXTRA_ORIENTATION, mOrientation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(HomeActivity.EXTRA_ORIENTATION)) {
                mOrientation = (Orientation) savedInstanceState.getSerializable(HomeActivity.EXTRA_ORIENTATION);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }



}