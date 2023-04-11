package com.rovaindu.homeservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.utils.views.TextViewAr;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

//    private TextView slideHeading, slideDescription;
//    private ImageView slide_imageView;


    public SliderAdapter(Context context) {

        this.context = context;
    }



    // heading Array
    public String[] heading_slide ={
            "ابدا عملك الان",
            "ابدا عملك الان",
            "ابدا عملك الان"
    };

    // description Array
    public String[] description_slide ={
            "يوفر لمقدمى الخدمات منصة سهلة الاستخدام لعرض خدماتهم بطريقة اكثر احترافية مع توفير طرق التواصل مع العميل لتقديم افضل خدمة",
           "يوفر لمقدمى الخدمات منصة سهلة الاستخدام لعرض خدماتهم بطريقة اكثر احترافية مع توفير طرق التواصل مع العميل لتقديم افضل خدمة",
            "يوفر لمقدمى الخدمات منصة سهلة الاستخدام لعرض خدماتهم بطريقة اكثر احترافية مع توفير طرق التواصل مع العميل لتقديم افضل خدمة"
    };




    @Override
    public int getCount() {

        return heading_slide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);


        TextViewAr slideHeading = view.findViewById(R.id.tvHeading);
        TextViewAr  slideDescription = view.findViewById(R.id.tvDescription);


        slideHeading.setText(heading_slide[position]);
        slideDescription.setText(description_slide[position]);

        return view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        View view = (View) object;
//        container.removeView(view);
//    }

}



