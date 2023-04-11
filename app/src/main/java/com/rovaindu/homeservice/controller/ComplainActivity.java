package com.rovaindu.homeservice.controller;

import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.adapter.ComplainCommentsAdapter;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.ComplainComments;
import com.rovaindu.homeservice.retrofit.models.Complain;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.FormatterUtil;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ComplainActivity extends BaseActivity {

    private Toolbar toolbar;
    private ScrollView scrollView;
    private ViewGroup likesContainer;

    private TextView titleTextView;

    private ProgressBar commentsProgressBar;
    private TextView warningCommentsTextView;

    //=========== Comments =============
    private RecyclerView commentsRecyclerView;
    private ComplainCommentsAdapter commentsRecyclerAdapter;
    private List<ComplainComments> commentsList;
    private EditText commentEditText;
    private Button sendButton;
    private int userPage = 1;
    private Complain complain;
    private TextViewAr complainTitle ,complainContent , complainDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.complain));

        RelativeLayout notification_panel = findViewById(R.id.notification_panel);
        notification_panel.setVisibility(View.VISIBLE);

        ImageView searchImg = findViewById(R.id.search);
        searchImg.setVisibility(View.VISIBLE);
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComplainActivity.this, SearchActivity.class);
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


        complain = (Complain) getIntent().getExtras().getSerializable(Constants.BUNDLE_COMPLAIN_LIST);


        init();
        initListeners();
        //UserImage
        //Glide.with(this).load(post.getThumbImage()).into(authorImageView);
        //Glide.with(this).load(blogPost.getImage_url()).into(postImageView);
        //UserName
        complainTitle.setText(complain.getTitle());
        complainContent.setText(complain.getBody());

        //PostTime
        //  String lastSeenTime = GetTimeAgo.getTimeAgo(post.getCreatedAt(), this);
        //CharSequence date = FormatterUtil.getRelativeTimeSpanString(this, complain.getUpdatedAt());

        complainDate.setText(complain.getUpdatedAt());

    }

    private void init() {

        complainDate = findViewById(R.id.complainDate);
        complainContent = findViewById(R.id.complainContent);
        complainTitle = findViewById(R.id.complainTitle);

        titleTextView = findViewById(R.id.titleTextView);

        scrollView = findViewById(R.id.scrollView);

        likesContainer = findViewById(R.id.likesContainer);

        //authorImageView = findViewById(R.id.authorImageView);

        commentsProgressBar = findViewById(R.id.commentsProgressBar);
        warningCommentsTextView = findViewById(R.id.warningCommentsTextView);

        commentsList = new ArrayList<>();
        //commentsList = complain.getComplainComments();TODO
        commentEditText = findViewById(R.id.commentEditText);
        sendButton = findViewById(R.id.sendButton);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);




    }
    private void initListeners() {

        // ExpandableListView on child click listener
        updateRecycleView();
        loadComments(userPage);
        commentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sendButton.setEnabled(charSequence.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(commentEditText.getText()!= null)
                {
                    sendComment();
                }




            }
        });

    }

    private void sendComment() {

        if (!ServiesSharedPrefManager.getInstance(this).isLoggedIn()) {
            Toasty.error(this, getResources().getString(R.string.you_have_to_reg_to_comment), Toast.LENGTH_SHORT, true).show();
            // Toast.makeText(this, getResources().getString(R.string.you_have_to_reg_to_comment), Toast.LENGTH_SHORT).show();

        }
        else {

            final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            int owner_id = ServiesSharedPrefManager.getInstance(this).getUser().getId();

            String owner_name = ServiesSharedPrefManager.getInstance(this).getUser().getName();
            ComplainComments complainComments  = new  ComplainComments(10 , 1 ,  "محمد الدوسرى" , "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg"
                    ,"هناك حقبة مثبتة منذ زمن طويل وهى ان المحتوى المقروء لصفحة ما يحتوى سيلهى القارىء عن التركيز على الشكل الخارجى هناك حقيقة مثبتة منذ زمن طويل"  , 500  , 1500  , timestamp.getTime());

            //Toast.makeText(this, "" + owner_name, Toast.LENGTH_SHORT).show();
            commentEditText.setText("");
            commentsList.add(complainComments);
            commentsRecyclerAdapter.notifyDataSetChanged();
            //Toasty.success(ComplainActivity.this, getResources().getString(R.string.comment_succ), Toast.LENGTH_SHORT, true).show();

        }
    }

    private void updateRecycleView() {
        //RecyclerView Firebase List


        commentsRecyclerAdapter = new ComplainCommentsAdapter(this , complain , commentsList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
//        commentsRecyclerView.setHasFixedSize(true);
        commentsRecyclerView.setLayoutManager(mLayoutManager);
        //mLayoutManager.setReverseLayout(true);

        /* TODO WITH RETROFIT
        commentsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                userPage++;
                loadComments(userPage);
            }
        });

         */
        commentsRecyclerView.setAdapter(commentsRecyclerAdapter);
        //mUserDatabase.keepSynced(true);
    }
    private void loadComments(int pageno)
    {


        commentsRecyclerAdapter.notifyDataSetChanged();

    }


}