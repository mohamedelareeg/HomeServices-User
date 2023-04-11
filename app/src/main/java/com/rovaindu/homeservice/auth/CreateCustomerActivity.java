package com.rovaindu.homeservice.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.interfaces.OnEmailCheckListener;
import com.rovaindu.homeservice.interfaces.OnUsernameCheckListener;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CreateCustomerActivity extends BaseActivity {


    private ImageView profilePic;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private TextView signinTextView;
    private LottieAnimationView emailCheck;
    private LottieAnimationView usernameCheck;
    private LottieAnimationView passwordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        Window w = getWindow();
        w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //initializing views
        profilePic = findViewById(R.id.signup_details_pic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(0);
            }
        });
        emailEditText = findViewById(R.id.signin_email_editText);
        usernameEditText = findViewById(R.id.signin_user_editText);
        passwordEditText = findViewById(R.id.signin_password_editText);
        signupButton = findViewById(R.id.signin_button);
        signinTextView = findViewById(R.id.signup_textview);
        emailCheck = findViewById(R.id.signup_email_check);
        usernameCheck = findViewById(R.id.signup_username_check);
        passwordCheck = findViewById(R.id.signup_password_check);


        emailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    emailCheck.setAnimation(R.raw.loading);
                    emailCheck.playAnimation();
                    if (isEmailValid(emailEditText.getText().toString())){
                        isCheckEmail(emailEditText.getText().toString(), new OnEmailCheckListener() {
                            @Override
                            public void onSuccess(boolean isRegistered) {
                                if (isRegistered){
                                    emailCheck.setAnimation(R.raw.error);
                                    emailCheck.playAnimation();
                                }else {
                                    emailCheck.setAnimation(R.raw.success_checkmark);
                                    emailCheck.playAnimation();
                                }
                            }
                        });
                    } else {
                        emailCheck.setAnimation(R.raw.error);
                        emailCheck.playAnimation();
                    }
                }
                return false;
            }
        });

        usernameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    usernameCheck.setAnimation(R.raw.loading);
                    usernameCheck.playAnimation();
                    if (usernameEditText.getText().length() > 0){
                        isCheckUsername(usernameEditText.getText().toString(), new OnUsernameCheckListener() {
                            @Override
                            public void onSuccess(boolean isRegistered) {
                                if (isRegistered){
                                    usernameCheck.setAnimation(R.raw.error);
                                    usernameCheck.playAnimation();
                                }else {
                                    usernameCheck.setAnimation(R.raw.success_checkmark);
                                    usernameCheck.playAnimation();
                                }
                            }
                        });
                    }

                }
                return false;
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    if (passwordEditText.getText().length() < 4){
                        passwordCheck.setAnimation(R.raw.error);
                        passwordCheck.playAnimation();
                    }else {
                        passwordCheck.setAnimation(R.raw.success_checkmark);
                        passwordCheck.playAnimation();
                    }
                }
                return false;
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailEditText.getText().length() != 0
                        && usernameEditText.getText().length() != 0
                        && passwordEditText.getText().length() != 0){
                    attemptSignup(emailEditText.getText().toString(), usernameEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });

        signinTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateCustomerActivity.this, LoginActivity.class);
                View sharedView = findViewById(R.id.logo);
                String transName = "splash_anim";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(CreateCustomerActivity.this, sharedView, transName);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });

    }


    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void isCheckEmail(final String email,final OnEmailCheckListener listener){

        listener.onSuccess(true);

    }


    private void isCheckUsername(final String username, final OnUsernameCheckListener listener){
        if (!username.contains(" ")){

            listener.onSuccess(false);

        }else {

            listener.onSuccess(true);

        }

    }

    private void attemptSignup(String email, String username, String password){


        startActivity(new Intent(CreateCustomerActivity.this, CreateCustomerDetailsActivity.class));
        CreateCustomerActivity.this.finish();
    }

    /*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateCustomerActivity.this, LoginActivity.class);
        View sharedView = findViewById(R.id.logo);
        String transName = "splash_anim";
        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(CreateCustomerActivity.this, sharedView, transName);
        startActivity(intent, transitionActivityOptions.toBundle());
        super.onBackPressed();
    }

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {

                Glide.with(CreateCustomerActivity.this).load(source).into(profilePic);
            }
        });
    }

    private void openGallery(int type) {

        EasyImage.openGallery(this, type);
    }
}