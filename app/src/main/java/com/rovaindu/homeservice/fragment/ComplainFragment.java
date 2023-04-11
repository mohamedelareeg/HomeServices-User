package com.rovaindu.homeservice.fragment;

import android.content.Intent;
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
import com.rovaindu.homeservice.adapter.ComplainsAdapter;
import com.rovaindu.homeservice.controller.ContactUsActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.ComplainComments;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Complain;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.CategoryResponse;
import com.rovaindu.homeservice.retrofit.response.ComplainsResponse;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComplainFragment extends Fragment {

    private TextViewAr OrderNotify;
    private List<ComplainComments> complainCommentsList;
    private List<Complain> complainList;
    private ComplainsAdapter complainsAdapter;
    private RecyclerView recComplain;
    private int userPage = 1;
    public ComplainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain, container, false);
        // Inflate the layout for this fragment
        ((HomeActivity) Objects.requireNonNull(getActivity())).appname.setText(getResources().getString(R.string.complain));
        // Inflate the layout for this fragment

        recComplain = view.findViewById(R.id.recComplains);
        OrderNotify = view.findViewById(R.id.OrderNotify);
        AssignAgentsList();
        LoadComplains();
        //getOrders(userPage);
        OrderNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
            }
        });
        return view;
    }

    private void getOrders(int userPage) {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final long currentTime = 1511645157000L;

        complainCommentsList.add(new ComplainComments(1 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(2 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(3 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(4 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(5 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(6 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));
        complainCommentsList.add(new ComplainComments(7 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , currentTime));

        /*
        complainList.add(new Complain(1, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(2, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(3, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(4, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(5, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(6, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(7, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));
        complainList.add(new Complain(8, 1 ,  "العنوان الخاص بالشكوى"  , "هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , currentTime ,complainCommentsList , 1200 , 750));


         */
        complainsAdapter.notifyDataSetChanged();

    }

    private void AssignAgentsList(){
        complainList = new ArrayList<>();
        complainCommentsList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        recComplain.setLayoutManager(mLayoutManager);
        recComplain.setItemAnimator(new DefaultItemAnimator());
        recComplain.setHasFixedSize(true);
        recComplain.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        complainsAdapter = new ComplainsAdapter(getActivity() , complainList);
        recComplain.setAdapter(complainsAdapter);
        //agentAdapter.notifyDataSetChanged()

    }
    private void LoadComplains(){

        ServiesUser user = ServiesSharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {



                Call<ComplainsResponse> call_login = service.getAllComplain(

                );
                call_login.enqueue(new Callback<ComplainsResponse>() {
                    @Override
                    public void onResponse(Call<ComplainsResponse> call, Response<ComplainsResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                complainList.addAll(response.body().getData());
                                complainsAdapter.notifyDataSetChanged();
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
                    public void onFailure(Call<ComplainsResponse> call, Throwable t) {
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