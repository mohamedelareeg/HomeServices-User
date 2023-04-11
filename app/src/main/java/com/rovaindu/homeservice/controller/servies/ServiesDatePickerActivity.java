package com.rovaindu.homeservice.controller.servies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.AgentDatePickerAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.SearchActivity;

import com.rovaindu.homeservice.model.AgentAvaliability;
import com.rovaindu.homeservice.model.SelectedDate;
import com.rovaindu.homeservice.retrofit.Unavailable;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.models.ServiesCategory;
import com.rovaindu.homeservice.retrofit.models.WorkTime;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ServiesDatePickerActivity extends BaseActivity {

    private Toolbar toolbar;
    ServiesAgent agent;
    ServiesCategory category;
    private AgentDatePickerAdapter agentAdapter;
    private RecyclerView recAgent;
    private int userPage = 1;
    private TextView currentCalender;
    private ImageView nextBtn ,backBtn;
    Date currentTime = Calendar.getInstance().getTime();
    Date selectedDate = currentTime;
    Locale locale = new Locale("ar");
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM", Locale.getDefault());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);//dd:MM:yyyy
    SimpleDateFormat day = new SimpleDateFormat("EEEE dd-MMM", locale);
    String formattedDate;
    String ServerformattedDate;
    private AgentAvaliability agentAvaliabilityList;
    private ArrayList<AgentAvaliability.DayAvaliabilty> dayAvaliabilties;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servies_date_picker);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.choose_servies_time));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiesDatePickerActivity.this, SearchActivity.class);
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

        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);
        category = (ServiesCategory) getIntent().getExtras().getSerializable(Constants.BUNDLE_CATEGORIES_LIST);
        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);
        currentCalender = findViewById(R.id.currentCalender);
        //pojoAvaliability();
        recAgent = findViewById(R.id.recAgentAvalibility);

        dayAvaliabilties = new ArrayList<>();
        AssignAgentsList();

        reAssignAvaliableTime(agent);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                calendar.add(Calendar.DAY_OF_YEAR, +1);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                selectedDate = calendar.getTime();
                Log.d("DATE", "onClick: " + day.format(selectedDate));

                dayAvaliabilties.clear();
                reAssignAvaliableTime(agent);
                Log.d("DATE", "onClick: " + ServerformattedDate);
                agentAdapter.notifyDataSetChanged();

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                selectedDate = calendar.getTime();
                Log.d("DATE", "onClick: " + day.format(selectedDate));
                dayAvaliabilties.clear();
                reAssignAvaliableTime(agent);
                Log.d("DATE", "onClick: " + ServerformattedDate);
                agentAdapter.notifyDataSetChanged();

            }
        });



    }


    private void reAssignAvaliableTime(ServiesAgent serviesAgent) {


        formattedDate = df.format(selectedDate);
        ServerformattedDate = sdf.format(selectedDate);
        currentCalender.setText(formattedDate);

        Log.d("DATE", "onClick: " + day.format(selectedDate));

        String startTime = serviesAgent.getWorkTime().getStart();
        String endtime = serviesAgent.getWorkTime().getEnd();
        String[] values_start = startTime.split(":");
        String[] values_end = endtime.split(":");
        int startHour = Integer.parseInt(values_start[0]);
        int startMin = Integer.parseInt(values_start[1]);//30
        int endHour = Integer.parseInt(values_end[0]);
        int endMin = Integer.parseInt(values_end[1]);

        double smin = (float)(startMin / 60);
        double emin = (float)(endMin / 60);
        int diff = endHour  - startHour;
        Log.d("DATE", "min: " + smin);
        for (int i = 0 ; i < diff ; i++)
        {
            int startT = startHour + i;
            int startTend = startT + 1;
            int startPMAM = ((startT / 12) >= 1 ) ? (startT - 12) : startT;
            int endPMAM = ((startTend / 12) >= 1 ) ? (startTend - 12) : startTend;

            if(startT < endHour) {
                String startedTXT = getHHMME(startT , startMin);
                String startTXT = getHHMM(startPMAM , startMin);
                String endTXT = getHHMM(endPMAM , endMin);
                String startTPMAM = ((startT / 12) >= 1 ) ? "م" : "ص";
                String endTPMAM = ((startTend / 12) >= 1 ) ? "م" : "ص";
                //Log.d("DATE", "reAssignAvaliableTime: " + startedTXT +":00");
                Unavailable selectedDate = new Unavailable(ServerformattedDate , startedTXT +":00");


                if(serviesAgent.getWorkTime().getUnavailable().contains(selectedDate))
                {
                    dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty(startTXT + " " + startTPMAM + " - " + endTXT + " " + endTPMAM, startedTXT + ":00", 2));
                    Log.d("DATE", "reAssignAvaliableTime: unavailable");
                }
                else
                {

                    dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty(startTXT + " " + startTPMAM + " - " + endTXT + " " + endTPMAM, startedTXT + ":00", 1));
                }


/*
                Log.d("DATE", "reAssignAvaliableTime: " + serviesAgent.getWorkTime().getUnavailable().size());
                for (int x = 0 ; x < serviesAgent.getWorkTime().getUnavailable().size() ; x++)
                {
                    Log.d("DATE", "reAssignAvaliableTime: " + serviesAgent.getWorkTime().getUnavailable().get(x).getDay() + "  " + ServerformattedDate);
                    Log.d("DATE", "reAssignAvaliableTime: " + serviesAgent.getWorkTime().getUnavailable().get(x).getHour() + "  " + startedTXT +":00");
                    //ServerformattedDate
                    //if(serviesAgent.getWorkTime().getUnavailable().get(x).equals(selectedDate))
                    if(serviesAgent.getWorkTime().getUnavailable().get(x).getDay().equals(ServerformattedDate) && serviesAgent.getWorkTime().getUnavailable().get(x).getHour().equals(startedTXT +":00"))
                    {
                        AgentAvaliability.DayAvaliabilty newDate = new AgentAvaliability.DayAvaliabilty(startTXT + " " + startTPMAM + " - " + endTXT + " " + endTPMAM, startedTXT + ":00", 2);

                        if(!dayAvaliabilties.contains(newDate)) {
                            dayAvaliabilties.add(newDate);
                        }
                        Log.d("DATE", "reAssignAvaliableTime: unavailable");

                    }
                    else
                    {
                        AgentAvaliability.DayAvaliabilty newDate = new AgentAvaliability.DayAvaliabilty(startTXT + " " + startTPMAM + " - " + endTXT + " " + endTPMAM, startedTXT + ":00", 1);
                        if(!dayAvaliabilties.contains(newDate)) {
                            dayAvaliabilties.add(newDate);
                        }
                    }
                }

 */
                //dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty(startTXT + " " + startTPMAM + " - " + endTXT + " " + endTPMAM, startedTXT + ":00", 1));



                //dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty(startT +":" + startMin +"م" + "-" + startTend +":" + startMin +"م", startT + ":" + startMin + ":00", 1));
            }
        }

        //dayAvaliabilties.add(new AgentAvaliability.DayAvaliabilty( "11:30م - 12:30ص" , "23:30:00",2));

        agentAvaliabilityList = new AgentAvaliability( 1 , ServerformattedDate , day.format(selectedDate),dayAvaliabilties);
        //agentAvaliabilityList.add(new AgentAvaliability( 1 , ServerformattedDate , day.format(selectedDate),dayAvaliabilties));
    }

    public String getHHMM(int hour , int min)
    {
        return String.format("%02d:%02d", hour, min);
    }
    public String getHHMME(int hour , int min)
    {
        return String.format(Locale.ENGLISH,"%02d:%02d", hour, min);
    }
    /*Output: xx:xx */
    public String fromMinutesToHHmm(int minutes, int hm) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", remainMinutes, hours);
    }
    public String fromMinutesToHH(int minutes, int hm) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        if(hm == 0)
        {
            return String.format("%02d:%02d", remainMinutes, hours);
        }
        else
        {
            return String.format("%02d:%02d", remainMinutes, hours);
        }

    }
    public String fromMinutesToHHE(int minutes , int hm) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        if(hm == 0)
        {
            return String.format(Locale.ENGLISH,"%02d:%02d", remainMinutes, hours);
        }
        else
        {
            return String.format(Locale.ENGLISH, "%02d:%02d", remainMinutes , hours);
        }

    }
    public String fromMinutesToHHmmE(int minutes , int hm) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format(Locale.ENGLISH, "%02d:%02d",  remainMinutes, hours);
    }
    private void AssignAgentsList(){
        agentAvaliabilityList = new AgentAvaliability( 1 , ServerformattedDate , day.format(selectedDate),dayAvaliabilties);

        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);

        recAgent.setLayoutManager(mLayoutManager);
        recAgent.setItemAnimator(new DefaultItemAnimator());
        recAgent.setHasFixedSize(true);
        recAgent.setNestedScrollingEnabled(false);
        /* TODO USELESS WITHOUT DATABASE
        recCategory.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                getData(userPage);

            }
        });

         */
        agentAdapter = new AgentDatePickerAdapter(this , agent , agentAvaliabilityList , dayAvaliabilties , category);
        recAgent.setAdapter(agentAdapter);
        //agentAdapter.notifyDataSetChanged()

    }



}