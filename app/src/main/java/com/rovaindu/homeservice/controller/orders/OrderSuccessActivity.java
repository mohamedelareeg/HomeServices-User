package com.rovaindu.homeservice.controller.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.controller.SearchActivity;
import com.rovaindu.homeservice.manager.ServiesBase;
import com.rovaindu.homeservice.retrofit.models.ServiesOrder;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;

public class OrderSuccessActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView orderNumberValue;
    private TextViewAr btnContinue , btnCancel ;
    private long orderID;
    private ServiesOrder order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.order_details));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSuccessActivity.this, SearchActivity.class);
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

        orderID =  getIntent().getLongExtra(Constants.BUNDLE_ORDER_NUMBER ,0);
        order = (ServiesOrder) getIntent().getExtras().getSerializable(Constants.BUNDLE_ORDER_LIST);
        orderNumberValue = findViewById(R.id.orderNumberValue);
        orderNumberValue.setText(String.valueOf(1));
        btnContinue = findViewById(R.id.btnContinue);
        btnCancel = findViewById(R.id.btnCancel);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSuccessActivity.this, TrackActivity.class);
                i.putExtra(Constants.BUNDLE_ORDER_LIST, (Serializable) order);
                startActivity(i);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSuccessActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OrderSuccessActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
        ServiesBase.getInstance().dispose();
    }
}