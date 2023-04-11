package com.rovaindu.homeservice.controller.forgetpassword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.HomeActivity;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.auth.LoginActivity;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.CategoryOld;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.response.CheckForgetCode;
import com.rovaindu.homeservice.retrofit.response.ForgetPasswordResponse;
import com.rovaindu.homeservice.retrofit.response.RenewPassword;
import com.rovaindu.homeservice.retrofit.response.UserResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.Utilites;
import com.rovaindu.homeservice.utils.views.TextViewAr;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText etOtp  , etPassword , etRePassword;
    private TextInputLayout etpasswordPanel , etrepasswordPanel;
    private TextViewAr btnProcced , btnResend;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.forget_password));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        email = getIntent().getExtras().getString(Constants.EMAIL_ADDRESS);
        Log.d("REG", "onCreate: " + email);
        etOtp = findViewById(R.id.etOtp);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnProcced = findViewById(R.id.btnProcced);
        btnResend = findViewById(R.id.btnResend);

        etpasswordPanel = findViewById(R.id.etpasswordPanel);
        etrepasswordPanel = findViewById(R.id.etrepasswordPanel);
        etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etOtp.setError(null);
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
                etrepasswordPanel.setError(null);
                etpasswordPanel.setErrorEnabled(false); // disable error
                etrepasswordPanel.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etRePassword.setError(null);
                etrepasswordPanel.setError(null);
                etpasswordPanel.setErrorEnabled(false); // disable error
                etrepasswordPanel.setErrorEnabled(false); // disable error
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btnProcced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEmpty(etOtp)){
                    Log.d("REG", "onClick: 1" );
                    if(validateEmpty(etPassword)) {
                        Log.d("REG", "onClick: 2" );
                        if(validateEmpty(etRePassword)) {
                            Log.d("REG", "onClick: 3" );
                            if (getRating(etPassword.getText().toString())) {
                                Log.d("REG", "onClick: 4" );
                                if (etPassword.getText().length() >= 8) {
                                    Log.d("REG", "onClick: 5" );
                                    if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                                        Log.d("REG", "onClick: 6" );
                                        attemptReset(etOtp.getText().toString(), etPassword.getText().toString(), etRePassword.getText().toString() );
                                    } else
                                        etrepasswordPanel.setError(getResources().getString(R.string.password_doesnt_match));
                                } else
                                    etrepasswordPanel.setError(getResources().getString(R.string.error_short));
                            }
                            else
                            {
                                etrepasswordPanel.setError(getResources().getString(R.string.please_enter_valid_password));
                            }

                        } else
                            etrepasswordPanel.setError(getResources().getString(R.string.required));
                    }else
                        etpasswordPanel.setError(getResources().getString(R.string.required));
                }else
                    etOtp.setError(getResources().getString(R.string.required));
            }
        });
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptResnd(email);

            }
        });
    }
    private boolean validateEmpty(EditText editText)
    {
        if(editText.getText().toString().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean getRating(String password) throws IllegalArgumentException {
        if (password == null) {throw new IllegalArgumentException();}
        if (password.length() < 8) {
            etrepasswordPanel.setError(getResources().getString(R.string.error_short));
            return false;
        } // minimal pw length of 6
        if (password.toLowerCase().equals(password)) {
            etrepasswordPanel.setError(getResources().getString(R.string.please_use_atleast_one_uppercase_letter));
            return false;
        } // lower and upper case
        int numDigits= Utilites.getNumberDigits(password);
        if (numDigits > 0 && numDigits == password.length()) {
            etrepasswordPanel.setError(getResources().getString(R.string.please_enter_valid_password));
            return false;
        } // contains digits and non-digits
        return true;
    }
    private void attemptReset(String otp , String password, String repassword){


        ApiInterface service = RetrofitClient.retrofitWrite("ar" ).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", email)
                        .addFormDataPart("code", otp)
                        .build();
                Call<CheckForgetCode> call_login = service.check_forget_code(
                        requestBody
                );
                call_login.enqueue(new Callback<CheckForgetCode>() {
                    @Override
                    public void onResponse(Call<CheckForgetCode> call, Response<CheckForgetCode> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                //TODO POJO USER INFO
                                //TODO POJO USER INFO

                                Log.d("REG", "onClick: 7" );
                                RequestBody requestBody = new MultipartBody.Builder()
                                        .setType(MultipartBody.FORM)
                                        .addFormDataPart("email", email)
                                        .addFormDataPart("password", password)
                                        .addFormDataPart("password_confirmation", repassword)
                                        .build();
                                Call<RenewPassword> call_T = service.renew_password(
                                        requestBody
                                );
                                call_T.enqueue(new Callback<RenewPassword>() {
                                    @Override
                                    public void onResponse(Call<RenewPassword> call_T, Response<RenewPassword> response) {

                                        if(response.body() != null) {
                                            if (response.body().getErrors().size() > 0) {
                                                //etPassword.setError(response.body().getMessage());
                                                Log.d("REG", "onResponse: " + response.body().getMessage());
                                            } else {
                                                //TODO POJO USER INFO
                                                //TODO POJO USER INFO
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
                                                                showResetAlert();
                                                                ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                                                                startActivity(new Intent(ResetPasswordActivity.this, HomeActivity.class));
                                                                ResetPasswordActivity.this.finish();
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
                                                    }
                                                });



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
                                    public void onFailure(Call<RenewPassword> call_T, Throwable t) {
                                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                                        Log.d("REG", "onClick: 8" );
                                        etOtp.setError(getResources().getString(R.string.otp_error));
                                    }
                                });
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
                    public void onFailure(Call<CheckForgetCode> call, Throwable t) {
                        Log.d("REG", "onClick: 9" );
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        etOtp.setError(getResources().getString(R.string.otp_error));
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
    private void attemptResnd(String email ){


        ApiInterface service = RetrofitClient.retrofitWrite("ar" ).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", email)
                        .build();
                Call<ForgetPasswordResponse> call_login = service.forget_password(
                        requestBody
                );
                call_login.enqueue(new Callback<ForgetPasswordResponse>() {
                    @Override
                    public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                //TODO POJO USER INFO
                                //TODO POJO USER INFO

                                showConfirmedAlert();
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
                    public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                        Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                        //etOtp.setError(getResources().getString(R.string.invild_email_or_password));
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

    private void showConfirmedAlert(){

        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, getResources().getString(R.string.code_resened_successfuly_please_check_your_inbox), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.WHITE);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);

            snackbar.show();

        }
        catch (Exception e)
        {
            Toast.makeText(this, getResources().getString(R.string.code_resened_successfuly_please_check_your_inbox), Toast.LENGTH_SHORT).show();
        }


    }
    private void showResetAlert(){

        try {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(parentLayout, getResources().getString(R.string.password_changed_succ), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    });

            // Changing message text color
            snackbar.setActionTextColor(Color.WHITE);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);

            snackbar.show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, getResources().getString(R.string.password_changed_succ), Toast.LENGTH_SHORT).show();
        }

    }
}