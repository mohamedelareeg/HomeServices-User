package com.rovaindu.homeservice.controller.orders;

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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.OrderCartAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.manager.ServiesBase;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.ConfirmOrderResponse;
import com.rovaindu.homeservice.retrofit.response.DeleteOrderResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCartActivity extends BaseActivity {

    private Toolbar toolbar;
    private ServiesAgent agent;
    private ServiesCategory category;
    private OrderCartAdapter agentAdapter;
    private RecyclerView recAgent;
    private ServiesOrder order;

    private int userPage = 1;


    private ActionMode actionMode;
    private TextViewAr btnContinue , btnCancel;
    private List<Service> pendingAgentServiesList;
    long currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);

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
                Intent intent = new Intent(OrderCartActivity.this, SearchActivity.class);
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
        pendingAgentServiesList =  ServiesBase.getInstance().getmOrders();

        double total =0;
        /*
        for (int i = 0; i < pendingAgentServiesList.size(); i++) {
            total += pendingAgentServiesList.get(i).getPrice();
        }

         */
        order = (ServiesOrder) getIntent().getExtras().getSerializable(Constants.BUNDLE_ORDER_LIST);
        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);
        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);

        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        currentTime = timestamp.getTime();

        //int id, String title, int categoryID, int customerID, double total, double discount, UserAddress userAddress, Coupon coupon, AgentServies agentServies, String paymentMethod, String status, long created_at
       /*
        final Order order = new Order(category.getName() , category.getId() , SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(),
                total ,0 ,SharedPrefManager.getInstance(getApplicationContext()).getUserAddress() , null  , "" , "نشط" ,currentTime , agent);


        */

        recAgent = findViewById(R.id.recServies);
        btnContinue = findViewById(R.id.btnContinue);
        btnCancel = findViewById(R.id.btnCancel);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrder(order);

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        agentAdapter = new OrderCartAdapter(this , agent , ServiesBase.getInstance().getmOrders());
        recAgent.setAdapter(agentAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(
                // below statement: used at move and sort
                // new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                //ItemTouchHelper.LEFT)
                new OrderCartActivity.ItemTouchHandler(0,
                        ItemTouchHelper.LEFT)
        );
        helper.attachToRecyclerView(recAgent);

        agentAdapter.setListener(new OrderCartAdapter.ServiesAdapterListener() {
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
    private void MakeOrder(ServiesOrder order){


        ServiesUser user = ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("notes", "ملاحظات")
                        .build();

                Call<ConfirmOrderResponse> call_login = service.ConfirmOrder(
                        order.getId(),
                        requestBody
                );
                call_login.enqueue(new Callback<ConfirmOrderResponse>() {
                    @Override
                    public void onResponse(Call<ConfirmOrderResponse> call, Response<ConfirmOrderResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Intent i = new Intent(OrderCartActivity.this, OrderSuccessActivity.class);
                                i.putExtra(Constants.BUNDLE_ORDER_NUMBER, currentTime);
                                i.putExtra(Constants.BUNDLE_ORDER_LIST, (Serializable) response.body().getData());

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
                    public void onFailure(Call<ConfirmOrderResponse> call, Throwable t) {
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

    private void DeleteOrder(ServiesOrder order){


        ServiesUser user = ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("notes", "ملاحظات")
                        .build();

                Call<DeleteOrderResponse> call_login = service.DeleteOrder(
                        order.getId(),
                        requestBody
                );
                call_login.enqueue(new Callback<DeleteOrderResponse>() {
                    @Override
                    public void onResponse(Call<DeleteOrderResponse> call, Response<DeleteOrderResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {

                                Intent i = new Intent(OrderCartActivity.this, OrderFailureActivity.class);
                                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);
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
                    public void onFailure(Call<DeleteOrderResponse> call, Throwable t) {
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