package com.rovaindu.homeservice.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.utils.views.TextViewAr;

public class ProfileActivity extends BaseActivity {
    LinearLayout personalinfo, experience, review;
    TextViewAr personalinfobtn, experiencebtn, reviewbtn;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ratingBar = findViewById(R.id.profileRating);
        personalinfo = findViewById(R.id.personalinfo);
        experience = findViewById(R.id.experience);
        review = findViewById(R.id.review);
        personalinfobtn = findViewById(R.id.personalinfobtn);
        experiencebtn = findViewById(R.id.experiencebtn);
        reviewbtn = findViewById(R.id.reviewbtn);
        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);
        ratingBar.setRating(4);

        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.white));
                experiencebtn.setTextColor(getResources().getColor(R.color.black));
                reviewbtn.setTextColor(getResources().getColor(R.color.black));
                personalinfobtn.setBackgroundColor(getResources().getColor(R.color.blue_500));
                experiencebtn.setBackgroundColor(getResources().getColor(R.color.white));
                reviewbtn.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.black));
                experiencebtn.setTextColor(getResources().getColor(R.color.white));
                reviewbtn.setTextColor(getResources().getColor(R.color.black));
                personalinfobtn.setBackgroundColor(getResources().getColor(R.color.white));
                experiencebtn.setBackgroundColor(getResources().getColor(R.color.blue_500));
                reviewbtn.setBackgroundColor(getResources().getColor(R.color.white));

            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                experience.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.black));
                experiencebtn.setTextColor(getResources().getColor(R.color.black));
                reviewbtn.setTextColor(getResources().getColor(R.color.white));
                personalinfobtn.setBackgroundColor(getResources().getColor(R.color.white));
                experiencebtn.setBackgroundColor(getResources().getColor(R.color.white));
                reviewbtn.setBackgroundColor(getResources().getColor(R.color.blue_500));

            }
        });


    }
}