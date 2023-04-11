package com.rovaindu.homeservice.controller.servies;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.controller.map.MapActivity;
import com.rovaindu.homeservice.controller.map.base.AddressData;
import com.rovaindu.homeservice.manager.ServiesBase;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.map.PlacePicker;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.compoundbuttongroup.CompoundButtonGroup;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class ServiesSubCategoriesActivity extends BaseActivity {

    private Toolbar toolbar;
    private AddressData addressData;
    ServiesCategory category;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ImageView categoryImage;
    TextView categoryTXT;
    private CompoundButtonGroup compoundButtonGroup;
    TextViewAr btnProceed;
    boolean getLocation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servies_sub_categories);

        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.mCollasping);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        /*
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiesSubCategoriesActivity.this, SearchActivity.class);
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
        /* TODO Show CollapsingToolbarLayout title only when collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Title");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

         */
        ServiesBase.getInstance().dispose();
        btnProceed = findViewById(R.id.btnProcced);
        categoryImage = findViewById(R.id.category_image);
        categoryTXT = findViewById(R.id.category_title);
        compoundButtonGroup = findViewById(R.id.subCategoryGroup);
        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);
        if(ServiesSharedPrefManager.getInstance(getApplicationContext()).isCarted()) {
            boolean inserted = ServiesBase.getInstance().AssignOrder(ServiesSharedPrefManager.getInstance(getApplicationContext()).getServices());
        }
        if(category != null)
        {
            categoryTXT.setText(category.getName());
            Glide.with(this).load(category.getImage()).into(categoryImage);
            //categoryImage.setImageResource(category.getImage());
            LinkedHashMap<String, String> serviesMap = new LinkedHashMap<>();
            for (Service agentServies : category.getServices()) {

                serviesMap.put(String.valueOf(agentServies.getId()) , agentServies.getName());
            }
            compoundButtonGroup.setEntries(serviesMap);
            compoundButtonGroup.reDraw();
        }
        compoundButtonGroup.setOnButtonSelectedListener(new CompoundButtonGroup.OnButtonSelectedListener() {
            @Override
            public void onButtonSelected(int position, String value, boolean isChecked) {
                String checked  = getString(isChecked ? R.string.checked : R.string.unchecked);
                Service selectedServies = category.getServices().get(position);
               // Log.d("Servies", "onButtonSelected: " + selectedServies.getName() );
                //int id, int categoryID, int serviesID, String name, String desc, int image, int rate, int ratecount, double price
                /*
                PendingAgentServies pendingAgentServies = new PendingAgentServies(ServiesBase.getInstance().getmOrders().size() , category.getId() , selectedServies.getId(),
                        selectedServies.getName() ,"" , "" , 20 , 5 , 100);

                 */
                boolean checkAvaliabilty = ServiesBase.getInstance().CheckAvaliablty(selectedServies.getId());
                if(!checkAvaliabilty) {

                    boolean inserted = ServiesBase.getInstance().InsertOrder(selectedServies);
                    if(!inserted)
                    {
                        //Log.d("SERVIES", "onButtonSelected 2: " + inserted);
                        boolean checkAvaliabiltyT = ServiesBase.getInstance().CheckAvaliablty(selectedServies.getId());
                        if(checkAvaliabiltyT)
                        {
                            // Log.d("SERVIES", "onButtonSelected: 3" + checkAvaliabilty);
                            ServiesBase.getInstance().RemoveOrder(selectedServies);
                        }

                    }
                }
                else
                {
                    ServiesBase.getInstance().RemoveOrder(selectedServies);
                }

              //  Log.d("SERVIES", "onButtonSelected: " + inserted);
                /*
                for (int i = 0 ; i < ServiesBase.getInstance().getmOrders().size() ; i++)
                {
                    Log.d("SERVIES", "onButtonSelected: " + ServiesBase.getInstance().getmOrders().get(i).getId() + "|" + ServiesBase.getInstance().getmOrders().get(i).getName());
                }

                 */

                //Log.d("SERVIES", "onButtonSelected: " + ServiesBase.getInstance().getmOrders().toString());
                //Toast.makeText(ServiesSubCategoriesActivity.this, checked + ": " + value, Toast.LENGTH_SHORT).show();
            }
        });
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServiesBase.getInstance().getmOrders().size() > 0) {
                    ServiesSharedPrefManager.getInstance(getApplicationContext()).setServices(ServiesBase.getInstance().getmOrders());
                    GoogleMap();
                    /*
                    Intent i = new Intent(ServiesSubCategoriesActivity.this, AgentsActivity.class);
                    i.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
                    startActivity(i);

                     */
                    Log.d("SERVIES", "onClick: " + ServiesBase.getInstance().getmOrders().size());


                }
                else
                {
                    Toasty.error(ServiesSubCategoriesActivity.this,getResources().getString(R.string.please_select_one_servies), Toast.LENGTH_SHORT,true).show();

                }
            }
        });
    }

    private void GoogleMap()
    {
        Intent intent = new Intent(ServiesSubCategoriesActivity.this, MapActivity.class);
        intent.putExtra(Constants.BUNDLE_CATEGORIES_LIST, (Serializable) category);
        intent.putExtra(Constants.GOOGLE_MAP_API_KEY, getResources().getString(R.string.api_key));
        intent.putExtra(Constants.SHOW_LAT_LONG_INTENT , false);
        intent.putExtra(Constants.INITIAL_LATITUDE_INTENT, 27.92359728296756);
        intent.putExtra(Constants.INITIAL_LONGITUDE_INTENT, 30.322514064610004);
        intent.putExtra(Constants.INITIAL_ZOOM_INTENT, 2.0f);
        intent.putExtra(Constants.HIDE_MARKER_SHADOW_INTENT, false);
        intent.putExtra(Constants.MARKER_DRAWABLE_RES_INTENT, -1);
        intent.putExtra(Constants.MARKER_COLOR_RES_INTENT, -1);
        intent.putExtra(Constants.FAB_COLOR_RES_INTENT, R.color.primaryColorYellowBlack);
        intent.putExtra(Constants.PRIMARY_TEXT_COLOR_RES_INTENT, R.color.black);
        intent.putExtra(Constants.PRIMARY_TEXT_COLOR_RES_INTENT, -1);
        startActivity(intent);
    }
    private void placePicker() {
        Intent intent = new PlacePicker.IntentBuilder()
                .setGoogleMapApiKey(getResources().getString(R.string.api_key))
                .setLatLong(18.520430, 73.856743)
                .setMapZoom(19.0f)
                .setAddressRequired(true)
                .setFabColor(R.color.primaryColorYellowBlack)
                .setPrimaryTextColor(R.color.black)
                .build(this);
        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData =  data.getParcelableExtra(Constants.ADDRESS_INTENT);
                addressData.getAddressList();
                setAddressData(addressData);
                Location mLocation = new Location("");
                mLocation.setLatitude(addressData.getLatitude());
                mLocation.setLongitude(addressData.getLongitude());
                init(getAddressData());
                //   getCurrentAddress(mLocation);

            }
        }
    }

    private void init(AddressData _addressData) {
        Log.d("ADDRESS", "init: PlaceName " + addressData.getPlaceName());
        Log.d("ADDRESS", "init: Latitude " + addressData.getLatitude());
        Log.d("ADDRESS", "init: LongLatitude " + addressData.getLongitude());
        if(_addressData != null) {
            getLocation = true;
        }

    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }
}