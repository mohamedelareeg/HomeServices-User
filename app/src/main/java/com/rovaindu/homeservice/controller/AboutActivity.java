package com.rovaindu.homeservice.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.utils.ThemeUtil;
import com.rovaindu.homeservice.utils.views.TextViewAr;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout ratel, changelogl, licensesl, googleplusl, githubl, twitterl, appsl;
    String version;
    TextViewAr versionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionText = (TextViewAr) findViewById(R.id.app_version);
        versionText.setText(version);

        ratel = (LinearLayout) findViewById(R.id.rate);
        changelogl = (LinearLayout) findViewById(R.id.changelog);
        licensesl = (LinearLayout) findViewById(R.id.licenses);
        googleplusl = (LinearLayout) findViewById(R.id.googleplus);
        githubl = (LinearLayout) findViewById(R.id.github);
        twitterl = (LinearLayout) findViewById(R.id.twitter);
        appsl = (LinearLayout) findViewById(R.id.apps);


        ratel.setOnClickListener(this);
        changelogl.setOnClickListener(this);
        licensesl.setOnClickListener(this);
        googleplusl.setOnClickListener(this);
        githubl.setOnClickListener(this);
        twitterl.setOnClickListener(this);
        appsl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName())));
                break;

            case R.id.changelog:
                new Changelog(this).show();
                break;

            case R.id.licenses:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/fekraeducation")));
                break;

            case R.id.googleplus:
                Intent facebookIntent = getFBIntent(AboutActivity.this, "Mohamed.Elareeg");
                startActivity(facebookIntent);
                break;

            case R.id.github:
                OnWhatsApp();
                break;

            case R.id.twitter:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/BMOhaa")));
                break;

            case R.id.apps:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Mohamed+El+Sayed")));
                break;


        }
    }



    private void OnWhatsApp() {
        try {
            Uri uri = Uri.parse("smsto:" + "+201277637646");
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "");
            i.setPackage("com.whatsapp");
            startActivity(i);
        }
        catch (Exception e)
        {

        }

    }
    public Intent getFBIntent(Context context, String facebookId) {

        try {
            // Check if FB app is even installed
            context.getPackageManager().getPackageInfo("com.facebook.orca", 0);

            String facebookScheme = "http://m.me/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
        }
        catch(Exception e) {

            // Cache and Open a url in browser
            String facebookProfileUri = "http://m.me/" + facebookId;
            return new Intent(Intent.ACTION_VIEW, Uri.parse(facebookProfileUri));
        }

    }
}