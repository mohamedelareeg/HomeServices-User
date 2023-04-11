package com.rovaindu.homeservice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.OrdersAdapter;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.MyOrdersResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestFragment extends Fragment {
    ServiesAgent selectedAgent;
    //private ArrayList<PendingAgentServies> pendingAgentServiesList;
    //private ArrayList<AgentServies> agentServiesList;
    //private ArrayList<AgentAvaliability> agentAvaliabilityList;
    //private ArrayList<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;
    private List<ServiesOrder> pendingOrdersList;
    private OrdersAdapter ordersAdapter;
    private RecyclerView recOrder;
    private int userPage = 1;

    public RequestFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        // Inflate the layout for this fragment
        ((HomeActivity) Objects.requireNonNull(getActivity())).appname.setText(getResources().getString(R.string.requests));
        // Inflate the layout for this fragment

        pojoAgent();
        recOrder = view.findViewById(R.id.recOrders);
        AssignAgentsList();
        //getOrders(userPage);
        LoadOrders();
        return view;
    }

    private void getOrders(int userPage) {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final long currentTime = 1511645157000L;
        double total = 5000;
        ordersAdapter.notifyDataSetChanged();

    }

    private void LoadOrders(){


        ServiesUser user = ServiesSharedPrefManager.getInstance(getContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                Call<MyOrdersResponse> call_login = service.getAllOrders(

                );
                call_login.enqueue(new Callback<MyOrdersResponse>() {
                    @Override
                    public void onResponse(Call<MyOrdersResponse> call, Response<MyOrdersResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {

                                pendingOrdersList.addAll(response.body().getData());
                                ordersAdapter.notifyDataSetChanged();
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
                    public void onFailure(Call<MyOrdersResponse> call, Throwable t) {
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


    private void pojoAgent() {
        /*
        dayAvaliabilties = new ArrayList<>();
        agentAvaliabilityList = new ArrayList<>();
        agentServiesList = new ArrayList<>();
        UserAddress addressData = new UserAddress(27.097767097646255 , 31.16760905832052 , "Assiut Governorate, Egypt");

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



        agentServiesList.add(new AgentServies( 1,"السباكة" ,"هناك حقبة مثبتة منذ زمن طويل " ,
                R.drawable.pojo_agent_servies, 500 , 250 , 50));
        agentServiesList.add(new AgentServies( 2,"الكهرباء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 1500 , 500 , 150));
        agentServiesList.add(new AgentServies( 3,"البناء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 2500 , 1000 , 1500));
        agentServiesList.add(new AgentServies( 4,"الحدادة" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 250 , 50 , 10));


        selectedAgent = new Agent( 1 , "ياسر محمود القحطانى" , "مهندس كهرباء" , "السعودية, الرياض",
                5,554848448484L,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل" ,
                R.drawable.pojo_agent_1 ,1 ,1 ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل",
                500 , 150 , 1500 ,50 , agentAvaliabilityList , addressData  , agentServiesList);


         */

    }
    private void AssignAgentsList(){
        //pendingAgentServiesList = new ArrayList<>();
        pendingOrdersList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        recOrder.setLayoutManager(mLayoutManager);
        recOrder.setItemAnimator(new DefaultItemAnimator());
        recOrder.setHasFixedSize(true);
        recOrder.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        ordersAdapter = new OrdersAdapter(getActivity() , pendingOrdersList);
        recOrder.setAdapter(ordersAdapter);
        //agentAdapter.notifyDataSetChanged()

    }

}