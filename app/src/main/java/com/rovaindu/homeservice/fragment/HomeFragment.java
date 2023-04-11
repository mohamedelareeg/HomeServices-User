package com.rovaindu.homeservice.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.CategoryAdapter;
import com.rovaindu.homeservice.adapter.OffersAdapter;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.Offer;
import com.rovaindu.homeservice.model.SubCategories;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.CategoryResponse;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    // TODO REQUIRE IF USING RECYCLEVIEW
    private ArrayList<Service> agentServiesList;
    private ArrayList<SubCategories> subCategoriesList;
    private ArrayList<ServiesCategory> categoryList;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recCategory;

    private ArrayList<Offer> offerList;
    private OffersAdapter offersAdapter;
    private RecyclerView recOffer;

    private int userPage = 1;

    MaterialCardView inprogressPanel;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((HomeActivity) Objects.requireNonNull(getActivity())).appname.setText(getResources().getString(R.string.services));

        //TODO REQUIRE IF USING RECYCLEVIEW
        recCategory = view.findViewById(R.id.categories_recycler_view);
        recOffer = view.findViewById(R.id.offers_list_view);


        AssignOfferList();
        AssignCategoryList();
        LoadCategory();
        /*
        getCategoryData(userPage);

         */
        getOfferData(userPage);



        return view;
    }

    private void AssignCategoryList(){

        categoryList = new ArrayList<>();
        subCategoriesList = new ArrayList<>();
        agentServiesList = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

        recCategory.setLayoutManager(mLayoutManager);
        recCategory.setItemAnimator(new DefaultItemAnimator());
        recCategory.setHasFixedSize(true);
        recCategory.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        categoryAdapter = new CategoryAdapter( getActivity() , categoryList);
        recCategory.setAdapter(categoryAdapter);

    }
    private void AssignOfferList(){

        offerList = new ArrayList<>();

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recOffer.setLayoutManager(mLayoutManager);
        recOffer.setItemAnimator(new DefaultItemAnimator());
        recOffer.setHasFixedSize(true);
        recOffer.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        offersAdapter = new OffersAdapter( getActivity() , offerList);
        recOffer.setAdapter(offersAdapter);

    }

    private void getCategoryData(int userPage) {



        /*
        agentServiesList.add(new AgentServies( 1,"السباكة" ,"هناك حقبة مثبتة منذ زمن طويل " ,
                R.drawable.pojo_agent_servies, 500 , 250 , 50));
        agentServiesList.add(new AgentServies( 2,"الكهرباء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 1500 , 500 , 150));
        agentServiesList.add(new AgentServies( 3,"البناء" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 2500 , 1000 , 1500));
        agentServiesList.add(new AgentServies( 4,"الحدادة" ,"هناك حقبة مثبتة منذ زمن طويل ",
                R.drawable.pojo_agent_servies, 250 , 50 , 10));

        //POJO SubCategories
        subCategoriesList.add(new SubCategories( "خدمات السباكة",0 , 1));
        subCategoriesList.add(new SubCategories( "خدمات الكهرباء",0 , 2));
        subCategoriesList.add(new SubCategories( "مواد البناء",0 , 3));
        subCategoriesList.add(new SubCategories( "خدمات السباكة",0 , 4));
        subCategoriesList.add(new SubCategories( "خدمات الكهرباء",0 , 5));
        subCategoriesList.add(new SubCategories( "مواد البناء",0 , 6));
        subCategoriesList.add(new SubCategories( "اخرى",0 , 7));
        //PojoCategories
        categoryList.add(new Category( "خدمات السباكة الكهرباء",R.drawable.pojo_cat_elec , 1 , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات النظافة",R.drawable.pojo_cat_clean , 2 , 1 , subCategoriesList ,   agentServiesList));
        categoryList.add(new Category( "خدمات الديكورات وورق الجدران والباركية",R.drawable.pojo_cat_decor ,  3 , 1, subCategoriesList  , agentServiesList));
        categoryList.add(new Category( "خدمات المياة",R.drawable.pojo_cat_water , 4  , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات الستالايت",R.drawable.pojo_cat_sata , 5 , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات الاثاث",R.drawable.pojo_cata_furniture , 6 , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات السواتر والعوازل",R.drawable.pojo_cat_window , 7 , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات قيد البناء",R.drawable.pojo_cat_inprogress , 8 , 2 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "خدمات الاحهزة الكهربائية",R.drawable.pojo_cat_hdevices , 9 , 1 , subCategoriesList , agentServiesList));
        categoryList.add(new Category( "قريبا",R.drawable.pojo_cat_soon , 10 , 1 , null , null));
        categoryList.add(new Category( "قريبا",R.drawable.pojo_cat_soon , 11 , 1 , null, null));
        categoryList.add(new Category( "قريبا",R.drawable.pojo_cat_soon , 12 , 1 , null, null));
        categoryList.add(new Category( "قريبا",R.drawable.pojo_cat_soon , 13 , 1 , null, null));
        categoryList.add(new Category( "قريبا",R.drawable.pojo_cat_soon , 14 , 1 , null, null));
        categoryAdapter.notifyDataSetChanged();

         */



    }
    private void getOfferData(int userPage) {
        offerList.add(new Offer( "تجربة",R.drawable.pojo_offers , 1));
        offerList.add(new Offer( "تجربة",R.drawable.pojo_disc , 2));
        offerList.add(new Offer( "تجربة",R.drawable.pojo_aniv , 3));


        categoryAdapter.notifyDataSetChanged();



    }
    private void LoadCategory(){

        ServiesUser user = ServiesSharedPrefManager.getInstance(getActivity()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {



                Call<CategoryResponse> call_login = service.getAllCategories(

                );
                call_login.enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                categoryList.addAll(response.body().getData());
                                categoryAdapter.notifyDataSetChanged();
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
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
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