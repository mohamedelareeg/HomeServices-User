package com.rovaindu.homeservice.controller.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.OrderServiesAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.controller.map.base.AddressData;
import com.rovaindu.homeservice.manager.ServiesBase;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.AgentAvaliability;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.MakeOrderResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends BaseActivity {

    private Toolbar toolbar;
    ServiesAgent agent;
    ServiesCategory category;
    AddressData addressData;
    AgentAvaliability selectedDay;
    private AgentAvaliability.DayAvaliabilty selectedDate;
    private ArrayList<AgentAvaliability> agentsList;
    private OrderServiesAdapter agentAdapter;
    private RecyclerView recAgent;
    private int userPage = 1;
    private TextViewAr btnProcced , btnViewOrder , orderDate , orderTime , orderProvider , minCost;
    private List<Service> pendingAgentServiesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.order_info));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, SearchActivity.class);
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
        // orderDate , orderTime , orderProvider , minCost
        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);
        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);
        selectedDay =  (AgentAvaliability) getIntent().getExtras().getSerializable(Constants.BUNDLE_SELECTED_DAY);
        selectedDate =  (AgentAvaliability.DayAvaliabilty) getIntent().getExtras().getSerializable(Constants.BUNDLE_SELECTED_DATE);

        pendingAgentServiesList =  ServiesBase.getInstance().getmOrders();

        double total =0;
        /*
        for (int i = 0; i < pendingAgentServiesList.size(); i++) {
            total += pendingAgentServiesList.get(i).getPrice();
        }

         */

        orderDate = findViewById(R.id.orderDate);
        orderTime = findViewById(R.id.orderTime);
        orderProvider = findViewById(R.id.orderProvider);
        minCost = findViewById(R.id.minCost);
        orderDate.setText(selectedDay.getDayt());//("السبت .22 اغسطس
        orderTime.setText(selectedDate.getDate());
        orderProvider.setText(agent.getName());
        minCost.setText(agent.getHourlyWage() +" "+ getResources().getString(R.string.currency));// minCost.setText(total +" "+ getResources().getString(R.string.currency));

        btnProcced = findViewById(R.id.btnProcced);
        btnViewOrder = findViewById(R.id.btnViewOrder);
        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MakeOrder(agent , pendingAgentServiesList);
            }
        });
        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrder(agent , pendingAgentServiesList);
            }
        });
        recAgent = findViewById(R.id.recServies);
        AssignAgentsList();


    }

    private void AssignAgentsList(){

        agentsList = new ArrayList<>();
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
        agentAdapter = new OrderServiesAdapter(this , agent , pendingAgentServiesList);
        recAgent.setAdapter(agentAdapter);
        //agentAdapter.notifyDataSetChanged()

    }
    private void MakeOrder(ServiesAgent serviesAgent , List<Service> pendingAgentServies){


        Log.d("ORDER", "MakeOrder: " + selectedDay.getDay());
        Log.d("ORDER", "MakeOrder: " + selectedDate.getStartdate());
        Log.d("ORDER", "MakeOrder: " + serviesAgent.getLocation());
        ServiesUser user = ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("agent_id", String.valueOf(serviesAgent.getId()))
                        .addFormDataPart("location", serviesAgent.getLocation())
                        .addFormDataPart("location_notes", "ملاحظات")
                        .addFormDataPart("services[]", String.valueOf(1))
                        .addFormDataPart("services[]", String.valueOf(2))
                        .addFormDataPart("day","2020-10-28")
                        .addFormDataPart("hour", selectedDate.getStartdate())
                        .build();

                Call<MakeOrderResponse> call_login = service.make_order(
                        requestBody
                );
                call_login.enqueue(new Callback<MakeOrderResponse>() {
                    @Override
                    public void onResponse(Call<MakeOrderResponse> call, Response<MakeOrderResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Intent i = new Intent(OrderDetailsActivity.this, OrderCartActivity.class);
                                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);
                                i.putExtra(Constants.BUNDLE_ORDER_LIST, (Serializable) response.body().getData());
                                i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                                startActivity(i);
                            }

                        }
                        else
                        {
                            Log.d("REG", "onResponse: " + response.code());
                            Log.d("REG", "onResponse: " + response.message());
                            Log.d("REG", "onResponse: " + response.errorBody().toString());

                        }
                    }

                    @Override
                    public void onFailure(Call<MakeOrderResponse> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
            }
        });



    }

}