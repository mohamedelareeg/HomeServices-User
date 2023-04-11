package com.rovaindu.homeservice.controller.agents;

import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.controller.servies.ServiesDatePickerActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.UserAddress;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.io.Serializable;

public class ViewAgentActivity extends BaseActivity {


    private Toolbar toolbar;
    ServiesAgent agent;
    ServiesCategory category;
    ImageView agentImg;
    RatingBar agentRatingbar;
    TextViewAr agentName , agentJob , agentRating , agentLocation , agentExperinceCount , agentCost , agentDistance ,agentPhone ,agentDesc ,agentExpe , btnProcced;
    private final static int PLACE_PICKER_REQUEST = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agent);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.agents_details));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAgentActivity.this, SearchActivity.class);
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
        //agentName , agentJob , agentRating , agentLocation , agentExperinceCount , agentCost , agentDistance ,agentPhone ,agentDesc ,agentExpe
        agentImg = findViewById(R.id.agentImg);
        agentRatingbar = findViewById(R.id.agentRatingbar);
        agentName = findViewById(R.id.agentName);
        agentJob = findViewById(R.id.agentJob);
        agentRating = findViewById(R.id.agentRating);
        agentLocation = findViewById(R.id.agentLocation);
        agentExperinceCount = findViewById(R.id.agentExperinceCount);
        agentCost = findViewById(R.id.agentCost);
        agentDistance = findViewById(R.id.agentDistance);
        agentPhone = findViewById(R.id.agentPhone);
        agentDesc = findViewById(R.id.agentDesc);
        agentExpe = findViewById(R.id.agentExpe);
        btnProcced = findViewById(R.id.btnProcced);

        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);
        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);
        if(agent != null)
        {
            String[] separated = agent.getLocation().split(",");
            Double Lat =  Double.parseDouble(separated[0]);
            Double lLat =  Double.parseDouble(separated[1]);

            String distance = DistanceCalculateTest(Lat , lLat);
           Glide.with(getApplicationContext()).load(agent.getImage()).into(agentImg);
           //agentImg.setImageResource(agent.getImage());
           agentName.setText(agent.getName());
           agentJob.setText(agent.getJob().getName());
           agentLocation.setText(agent.getCountry().getName() + "," + agent.getCity().getName());
           float rating = 3.5f;
           //float rating = agent.getRate() / agent.getRatecount();
           agentRatingbar.setRating(rating);
           agentRating.setText("( " + getResources().getString(R.string.raring) + " " + rating + " )");
           agentExperinceCount.setText(getResources().getString(R.string.exper_years) + ": " + agent.getExperienceYears() +" " + getResources().getString(R.string.years) );
           agentCost.setText(getResources().getString(R.string.cost_per_hour) + ": " + agent.getHourlyWage() + " " + getResources().getString(R.string.currency));

           agentDistance.setText(getResources().getString(R.string.distance) + ": " +distance );
           agentPhone.setText( agent.getPhone());
           agentDesc.setText(agent.getExperience());
           agentExpe.setText(agent.getExperience());
        }
        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewAgentActivity.this, ServiesDatePickerActivity.class);
                i.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) agent);
                i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                startActivity(i);

            }
        });
        //DistanceCalculateTest(agent.getUserAddress());

    }

    private String DistanceCalculateTest(double Lat , double lLat) {
        UserAddress user =  ServiesSharedPrefManager.getInstance(getApplicationContext()).getUserAddressT();
        //Toast.makeText(this, "" + userAddress.getPlaceName(), Toast.LENGTH_SHORT).show();
        Log.d("ADDRESS", "init: PlaceName " + user.getPlaceName());
        Log.d("ADDRESS", "init: Latitude " + user.getLatitude());
        Log.d("ADDRESS", "init: LongLatitude " + user.getLongitude());


        Log.d("ADDRESS", "init: " + distFrom(Lat , lLat ,user.getLatitude() , user.getLongitude()) / 1000 + " KM");
        //Log.d("ADDRESS", "init: " + distance(addressData.getLatitude() , addressData.getLongitude() ,37.42199845544925 , -122.0839998498559 ,0 , 0)  + " KM");
        float dist = distFrom(Lat , lLat     ,user.getLatitude() , user.getLongitude()) / 1000;
        return   String.format("%.2f",dist) + " كم";
        //Toasty.success(ViewAgentActivity.this, context.getResources().getString(R.string.test_distance) +  distFrom(userAddress.getLatitude() , userAddress.getLongitude() ,user.getLatitude() , user.getLongitude()) / 1000 + " كم" , Toast.LENGTH_SHORT).show();
    }
    private void DistanceCalculateTest(UserAddress userAddress) {
        UserAddress user =  ServiesSharedPrefManager.getInstance(getApplicationContext()).getUserAddressT();
        //Toast.makeText(this, "" + userAddress.getPlaceName(), Toast.LENGTH_SHORT).show();
        Log.d("ADDRESS", "init: PlaceName " + user.getPlaceName());
        Log.d("ADDRESS", "init: Latitude " + user.getLatitude());
        Log.d("ADDRESS", "init: LongLatitude " + user.getLongitude());


        Log.d("ADDRESS", "init: " + distFrom(userAddress.getLatitude() , userAddress.getLongitude() ,user.getLatitude() , user.getLongitude()) / 1000 + " KM");
        //Log.d("ADDRESS", "init: " + distance(addressData.getLatitude() , addressData.getLongitude() ,37.42199845544925 , -122.0839998498559 ,0 , 0)  + " KM");
        Toasty.success(ViewAgentActivity.this, getResources().getString(R.string.test_distance) +  distFrom(userAddress.getLatitude() , userAddress.getLongitude() ,user.getLatitude() , user.getLongitude()) / 1000 + " كم" , Toast.LENGTH_SHORT).show();
    }
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


}