package com.rovaindu.homeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;

import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.UserTokenResponse;
import com.rovaindu.homeservice.utils.PermUtil;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity" ;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    public NavController navController;
    public NavigationView navigationView;
    TextViewAr customerName;
    ImageView customerIMG;
    public final static String EXTRA_ORIENTATION = "EXTRA_ORIENTATION";
    public final static String EXTRA_WITH_LINE_PADDING = "EXTRA_WITH_LINE_PADDING";
    public TextViewAr appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

        drawerLayout = findViewById(R.id.my_drawer);

        navigationView = findViewById(R.id.navigationView);
        //

        customerName = navigationView.getHeaderView(0).findViewById(R.id.customerName);
        customerIMG = navigationView.getHeaderView(0).findViewById(R.id.customerIMG);


        //
        mToggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.open , R.string.close);
        drawerLayout.addDrawerListener(mToggle);

        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);

        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        if (ServiesSharedPrefManager.getInstance(this).isLoggedIn()) {
            //registerGCM();
            customerName.setText(ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
            Glide.with(this).load(ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser().getImage()).into(customerIMG);
            //sendRegistrationTokenToServer(FirebaseInstanceId.getInstance().getToken()); TODO
            //Toast.makeText(this, String.valueOf( SharedPrefManager.getInstance(getApplicationContext()).getLastViewed()) + " " , Toast.LENGTH_SHORT).show();
        }


        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new HomeFragment())
                .commit();

         */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //Toast.makeText(MultiEditorActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            attemptToExitIfRoot(null);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();
        navigationView.setCheckedItem(R.id.nav_servies);
        switch (id) {

            case R.id.nav_servies:
                break;

            case R.id.nav_profile:
                //navigationView.getMenu().getItem(R.id.nav_profile).setIcon(R.drawable.ic_person);
                navController.navigate(R.id.nav_profile);
                break;
            case R.id.nav_contact:
                navController.navigate(R.id.nav_contact);
                break;
            case R.id.nav_requests:
                navController.navigate(R.id.nav_requests);
                break;
            case R.id.nav_complains:
                navController.navigate(R.id.nav_complains);
                break;
            case R.id.nav_auth:
                if(ServiesSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                    ServiesSharedPrefManager.getInstance(getApplicationContext()).logout();
                   // LoginManager.getInstance().logOut();
                    Intent loginIntent = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                break;


        }
        return true;

    }
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

    @Override
    protected void onStart() {
        if (ServiesSharedPrefManager.getInstance(this).isLoggedIn()) {
            registerGCM(FirebaseInstanceId.getInstance().getToken());

        }
        super.onStart();
    }

    private void registerGCM(String Token ){

        ServiesUser user = ServiesSharedPrefManager.getInstance(this).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fcm_token", Token)
                        .build();
                Call<UserTokenResponse> call_login = service.UpdateFCM(
                        requestBody
                );
                call_login.enqueue(new Callback<UserTokenResponse>() {
                    @Override
                    public void onResponse(Call<UserTokenResponse> call, Response<UserTokenResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                ServiesSharedPrefManager.getInstance(getApplicationContext()).userToken(response.body().getData().getObject().getFcmToken());//TODO
                                ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData().getObject());
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
                    public void onFailure(Call<UserTokenResponse> call, Throwable t) {
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