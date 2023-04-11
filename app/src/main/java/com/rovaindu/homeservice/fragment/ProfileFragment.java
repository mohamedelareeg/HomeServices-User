package com.rovaindu.homeservice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.controller.agents.ViewAgentActivity;
import com.rovaindu.homeservice.controller.profile.EditProfileActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;

import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.util.Objects;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    ImageView customerIMG;
    TextViewAr customerName , TxtPhone , TxtLocation , customerDesc;
    LinearLayout personalinfo, experience, review;
    TextViewAr personalinfobtn, experiencebtn, reviewbtn , btnEditProfile;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        ((HomeActivity) Objects.requireNonNull(getActivity())).appname.setText(getResources().getString(R.string.my_profile));

        //customerNam , TxtPhone , TxtLocation , customerDesc;
        customerName = view.findViewById(R.id.customerName);
        TxtPhone = view.findViewById(R.id.TxtPhone);
        TxtLocation = view.findViewById(R.id.TxtLocation);
        customerDesc = view.findViewById(R.id.customerDesc);
        customerIMG = view.findViewById(R.id.ImgLogo);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        personalinfo = view.findViewById(R.id.personalinfo);
        experience = view.findViewById(R.id.experience);
        review = view.findViewById(R.id.review);
        personalinfobtn = view.findViewById(R.id.personalinfobtn);
        experiencebtn = view.findViewById(R.id.experiencebtn);
        reviewbtn = view.findViewById(R.id.reviewbtn);
        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);

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

        if (ServiesSharedPrefManager.getInstance(getContext()).isLoggedIn()) {
            //registerGCM();
            customerName.setText(ServiesSharedPrefManager.getInstance(getContext()).getUser().getName());
            Glide.with(this).load(ServiesSharedPrefManager.getInstance(getContext()).getUser().getImage()).into(customerIMG);
            TxtPhone.setText( ServiesSharedPrefManager.getInstance(getContext()).getUser().getPhone());
            TxtLocation.setText(ServiesSharedPrefManager.getInstance(getContext()).getUser().getCity().getName() + ", " + ServiesSharedPrefManager.getInstance(getContext()).getUser().getCountry().getName());
            //sendRegistrationTokenToServer(FirebaseInstanceId.getInstance().getToken()); TODO
            //Toast.makeText(this, String.valueOf( SharedPrefManager.getInstance(getApplicationContext()).getLastViewed()) + " " , Toast.LENGTH_SHORT).show();
        }
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}