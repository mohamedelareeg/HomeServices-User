package com.rovaindu.homeservice.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.rovaindu.homeservice.MainActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.ThemeAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.interfaces.RecyclerViewClickListener;
import com.rovaindu.homeservice.model.Theme;
import com.rovaindu.homeservice.utils.LocaleHelper;
import com.rovaindu.homeservice.utils.ThemeUtil;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.ThemeView;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {


    public static List<Theme> mThemeList = new ArrayList<>();
    public static int selectedTheme = 0;
    private RecyclerView mRecyclerView;
    private ThemeAdapter mAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LinearLayout language_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.settings));
        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initBottomSheet();

        prepareThemeData();

        ThemeView themeView = findViewById(R.id.theme_selected);
        themeView.setTheme(mThemeList.get(selectedTheme));

        language_panel = findViewById(R.id.language_panel);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        final String data = prefs.getString("language", "ar"); //no id: default value
        language_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(data.equals("en"))
                {
                    LocaleHelper.setLocale(SettingsActivity.this, "ar");
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    storeLanguageInPref("ar");
                    finish();
                }
                else if(data.equals("ar"))
                {
                    LocaleHelper.setLocale(SettingsActivity.this, "en");
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    storeLanguageInPref("en");
                    finish();
                }
                else {
                    LocaleHelper.setLocale(SettingsActivity.this, "ar");
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    storeLanguageInPref("ar");
                    finish();
                }


            }
        });
    }

    //SharedPreferences  language = getActivity().getSharedPreferences("language",MODE_PRIVATE);
    private void storeLanguageInPref(String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("language", language);
        editor.apply();
    }
    private void initBottomSheet(){
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        mBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        SwitchCompat switchCompat = findViewById(R.id.switch_dark_mode);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mIsNightMode = b;
                int delayTime = 200;
                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    delayTime = 400;
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                compoundButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mIsNightMode){
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }else{
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                    }
                },delayTime);

            }
        });

        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new ThemeAdapter(mThemeList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SettingsActivity.this.recreate();
                    }
                },400);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareThemeData() {
        mThemeList.clear();
        mThemeList.addAll(ThemeUtil.getThemeList());
        mAdapter.notifyDataSetChanged();
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.theme_selected :

        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}