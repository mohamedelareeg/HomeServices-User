package com.rovaindu.homeservice.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.forgetpassword.ForgetPasswordActivity;
import com.rovaindu.homeservice.controller.orders.OrderSuccessActivity;
import com.rovaindu.homeservice.controller.orders.TrackActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.UserAddress;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.response.UserResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import java.io.Serializable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private TextInputEditText etEmail , etPassword;
    private MaterialButton signinButton;
    private TextView signupText , viewForgetPassword;
    private Toolbar toolbar;
    private TextInputLayout txInputPassword , txInputEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow();
        w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.sign_in));
        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
        //initializing vars
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        signinButton = findViewById(R.id.signin_button);
        signupText = findViewById(R.id.signup_textview);
        txInputEmail = findViewById(R.id.txInputEmail);
        txInputPassword = findViewById(R.id.txInputPassword);
        viewForgetPassword = findViewById(R.id.viewForgetPassword);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txInputEmail.setErrorEnabled(false); // disable error
                txInputPassword.setErrorEnabled(false); // disable error
                if (isEmailValid(etEmail.getText().toString())){
                    if (etPassword.getText().length() > 6){
                        attemptLogin(etEmail.getText().toString(), etPassword.getText().toString());
                    }else {
                        txInputPassword.setError(getResources().getString(R.string.password_should_longer_than_six));
                    }
                }else {
                    txInputEmail.setError(getResources().getString(R.string.enter_valid_email));
                }
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etEmail.setError(null);

                txInputEmail.setErrorEnabled(false); // disable error

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPassword.setError(null);

                txInputPassword.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                View sharedView = findViewById(R.id.logo);
                String transName = "splash_anim";
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, sharedView, transName);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
        viewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        });

    }


    private void attemptLogin(String email, String password){


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", email)
                        .addFormDataPart("password", password)
                        .build();
                Call<UserResponse> call_login = service.Login(
                        requestBody
                );
                call_login.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Log.d("REG", "onResponse: "+response.body().getData().getEmail());
                                //TODO POJO USER INFO
                                ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                LoginActivity.this.finish();
                            }

                        }
                        else
                        {
                            Log.d("REG", "onResponse: " + response.code());
                            Log.d("REG", "onResponse: " + response.message());
                            Log.d("REG", "onResponse: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        txInputEmail.setError(getResources().getString(R.string.invild_email_or_password));
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REG", "onFailure: " + e.getLocalizedMessage());
            }
        });



    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}