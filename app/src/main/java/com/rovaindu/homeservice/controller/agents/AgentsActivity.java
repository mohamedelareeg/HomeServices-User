package com.rovaindu.homeservice.controller.agents;

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
import com.rovaindu.homeservice.adapter.AgentAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;

import com.rovaindu.homeservice.manager.ServiesBase;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.AgentsResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsActivity extends BaseActivity {

    private Toolbar toolbar;
    ServiesCategory category;
    /*
    private ArrayList<AgentServies> agentServiesList;
    private ArrayList<AgentAvaliability> agentAvaliabilityList;
    private ArrayList<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;

     */
    private ArrayList<ServiesAgent> agentList;
    private AgentAdapter agentAdapter;
    private RecyclerView recAgent;

    private int userPage = 1;

    private ShimmerFrameLayout shimmerFrameLayout;
    List<Service> pendingAgentServiesList;
    /*
    private ArrayList<WorkTime> workTimes;
    private ArrayList<WorkTime.Unavailable> unavailables;

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);


        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentsActivity.this, SearchActivity.class);
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

        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);
        pojoAgentInfo();
        if(category != null)
        {
            appname.setVisibility(View.VISIBLE);
            appname.setText(category.getName());
        }

        shimmerFrameLayout = findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmer();

        recAgent = findViewById(R.id.recAgents);
        pendingAgentServiesList =  ServiesBase.getInstance().getmOrders();
        AssignAgentsList();
        LoadAgents(pendingAgentServiesList);
        //getAgentsData(userPage);

    }

    private void pojoAgentInfo() {
        /*
        agentAvaliabilityList = new ArrayList<>();
        dayAvaliabilties = new ArrayList<>();
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "6:30م - 7:30م"  , "18:30:00", 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "7:30م - 8:30م" , "19:30:00",1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "8:30م - 9:30م" ,"20:30:00", 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "9:30م - 10:30م" ,"21:30:00", 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "10:30م - 11:30م" ,"22:30:00", 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "11:30م - 12:30ص" , "23:30:00",2));

        agentAvaliabilityList.add(new AgentAvaliability( 1 , "16-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 2 ,"17-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 3 , "18-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 4 , "19-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 5 , "20-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 6 ,"21-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 7 ,"22-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 8 ,"23-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 9 ,"24-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 10 ,"25-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 11,"26-9-2020",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 12 ,"27-9-2020",dayAvaliabilties));

         */
    }

    private void AssignAgentsList(){

        agentList = new ArrayList<>();
        /*
        agentAvaliabilityList = new ArrayList<>();
        dayAvaliabilties = new ArrayList<>();
        agentServiesList = new ArrayList<>();

         */
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
        agentAdapter = new AgentAdapter(this , agentList , category);
        recAgent.setAdapter(agentAdapter);


    }

    private void getAgentsData(int userPage) {
/*
        //Avaliable = 1
        //notAvaliable = 2
        //POJO SubCategories
        UserAddress addressData = new UserAddress(27.097767097646255 , 31.16760905832052 , "Assiut Governorate, Egypt");

        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "6:30م - 7:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "7:30م - 8:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "8:30م - 9:30م" , 1));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "9:30م - 10:30م" , 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "10:30م - 11:30م" , 2));
        dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "11:30م - 12:30ص" , 2));

        agentAvaliabilityList.add(new AgentAvaliability( 1 , "16/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 2 ,"17/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 3 , "18/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 4 , "19/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 5 , "20/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 6 ,"21/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 7 ,"22/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 8 ,"23/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 9 ,"24/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 10 ,"25/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 11,"26/9",dayAvaliabilties));
        agentAvaliabilityList.add(new AgentAvaliability( 12 ,"27/9",dayAvaliabilties));


        agentServiesList.add(new AgentServies( 1,"السباكة" ,"هناك حقبة مثبتة منذ زمن طويل " ,
                R.drawable.pojo_agent_servies, 500 , 250 , 50));
        agentServiesList.add(new AgentServies( 2,"الكهرباء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 1500 , 500 , 150));
        agentServiesList.add(new AgentServies( 3,"البناء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 2500 , 1000 , 1500));
        agentServiesList.add(new AgentServies( 4,"الحدادة" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 250 , 50 , 10));

        //PojoAgents
        //(int categoryid, String name, String job, String location, int countexperience, int phone, String jobexperience, int image, int id, int type, String desc, int rate, int ratecount, double cost, int distance)
        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_1 ,1 ,1 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData  , agentServiesList));
        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_2 ,2 ,1 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));

        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_3 ,3 ,1 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));

        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_4 ,1 ,4 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));

        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_1 ,1 ,5 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));

        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_2 ,1 ,6 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));

        agentList.add(new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_3 ,1 ,7 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData , agentServiesList));
        agentAdapter.notifyDataSetChanged();


 */
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        if (agentList.size() > 0) {
            recAgent.setVisibility(View.VISIBLE);
        }



    }

    private void LoadAgents(List<Service> mPendingServies){

        try {


            StringBuilder PendingServies = new StringBuilder("services[]=" + mPendingServies.get(0).getId());
            Map<String, String> data = new HashMap<>();
            for (int i = 0; i < mPendingServies.size(); i++) {
                //PendingServies.append("&services[]=" + mPendingServies.get(i).getServiesID());

                data.put("services[]", String.valueOf(mPendingServies.get(i).getId()));
            }
            ServiesUser user = ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser();
            ApiInterface service = RetrofitClient.retrofitAPIWrite("ar", user.getApiToken()).create(ApiInterface.class);

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {


                    Call<AgentsResponse> call_login = service.getAllAgents(
                            data
                    );
                    call_login.enqueue(new Callback<AgentsResponse>() {
                        @Override
                        public void onResponse(Call<AgentsResponse> call, Response<AgentsResponse> response) {

                            if (response.body() != null) {
                                if (response.body().getErrors().size() > 0) {
                                    Log.d("REG", "onResponse: " + response.body().getMessage());
                                } else {
                                /*
                                for(int i = 0; i < response.body().getData().size(); i++)
                                {
                                    ServiesAgent selectedAgent = response.body().getData().get(i);
                                    ServiesAgent serviesAgent = new ServiesAgent(selectedAgent.getId(),selectedAgent.getName(),selectedAgent.getEmail(),selectedAgent.getLocation(),selectedAgent.getPhone(),selectedAgent.getExperience(),selectedAgent.getExperienceYears(),selectedAgent.getHourlyWage(),selectedAgent.getExpireDate(),selectedAgent.getImage(),selectedAgent.getApiToken(),selectedAgent.getFcmToken(),selectedAgent.getPlan(),selectedAgent.getCountry(),selectedAgent.getCity(),selectedAgent.getJob(),selectedAgent.getServices(),agentAvaliabilityList);
                                    agentList.add(serviesAgent);
                                }

                                 */

                                    agentList.addAll(response.body().getData());
                                    agentAdapter.notifyDataSetChanged();
                                    shimmerFrameLayout.setVisibility(View.GONE);
                                    shimmerFrameLayout.stopShimmer();
                                    if (agentList.size() > 0) {
                                        recAgent.setVisibility(View.VISIBLE);
                                    }
                                }

                            } else {
                                Log.d("REG", "onResponse: " + response.code());
                                Log.d("REG", "onResponse: " + response.message());
                                Log.d("REG", "onResponse: " + response.errorBody().toString());

                            }
                        }

                        @Override
                        public void onFailure(Call<AgentsResponse> call, Throwable t) {
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
        catch (Exception e)
        {

        }

    }

}