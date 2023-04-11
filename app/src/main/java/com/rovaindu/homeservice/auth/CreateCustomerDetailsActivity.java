package com.rovaindu.homeservice.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.rovaindu.homeservice.controller.map.base.AddressData;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.model.UserAddress;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.City;
import com.rovaindu.homeservice.retrofit.models.Country;
import com.rovaindu.homeservice.retrofit.response.CityResponse;
import com.rovaindu.homeservice.retrofit.response.CountryResponse;
import com.rovaindu.homeservice.retrofit.response.UserRegisterResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.Utilites;
import com.rovaindu.homeservice.utils.map.PlacePicker;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCustomerDetailsActivity extends BaseActivity {

    private AutoCompleteTextView main_ip;
    private Spinner spinner_main_ip;
    private ArrayAdapter<String> adapter_main;

    private AutoCompleteTextView sub_ip;
    private Spinner spinner_sub_ip;
    private ArrayAdapter<String> adapter_sub;

    private String check;
    private AddressData addressData;
    private RelativeLayout locationPanel;
    private TextInputLayout txFirstName , txInputPassword , txInputrePassword , txInputEmail  , txInputPhone;
    private TextInputEditText etFirstName , etPassword , etrePassword , etEmail   , etPhone;
    private MaterialButton finishButton;
    private int GovermentID = -1 , CityID = -1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer_details);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.new_customer_register));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        txFirstName = findViewById(R.id.txFirstName);
        txInputPassword = findViewById(R.id.txInputPassword);
        txInputrePassword = findViewById(R.id.txInputrePassword);
        txInputEmail = findViewById(R.id.txInputEmail);
        txInputPhone = findViewById(R.id.txInputPhone);
        locationPanel = findViewById(R.id.locationPanel);

        main_ip = (AutoCompleteTextView) findViewById(R.id.main_ip);
        sub_ip = (AutoCompleteTextView) findViewById(R.id.sub_ip);

        spinner_main_ip = (Spinner) findViewById(R.id.spinner_main_ip);
        spinner_sub_ip = (Spinner) findViewById(R.id.spinner_sub_ip);

        etFirstName = findViewById(R.id.etFirstName);
        etPassword = findViewById(R.id.etPassword);
        etrePassword = findViewById(R.id.etrePassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        finishButton = findViewById(R.id.signup_finish_button);
        getMainCategory();
        locationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePicker();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etFirstName.getText().length() > 0) {
                    if (isEmailValid(etEmail.getText().toString())) {
                        if (getRating(etPassword.getText().toString())) {
                            if (getGovermentID() != -1) {
                                if (getCityID() != -1) {
                                    if (addressData != null) {
                                        if (etPhone.getText().length() == 11) {
                                            if (etPassword.getText().toString().equals(etrePassword.getText().toString())) {
                                                attemptLogin(etFirstName.getText().toString(), getGovermentID(), getCityID(), etEmail.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString());
                                            } else
                                                txInputrePassword.setError(getResources().getString(R.string.password_doesnt_match));
                                        } else {
                                            txInputPhone.setError(getResources().getString(R.string.please_enter_valid_phone));
                                        }
                                    } else {
                                        Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_your_current_location), Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                            }
                        }
                    } else {
                        txInputEmail.setError(getResources().getString(R.string.enter_valid_email));
                    }
                }
                else
                {
                    txFirstName.setError(getResources().getString(R.string.pleae_enter_first_name));
                }

            }
        });
        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etFirstName.setError(null);

                txFirstName.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

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
                etrePassword.setError(null);
                txInputrePassword.setErrorEnabled(false);
                txInputPassword.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etrePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPassword.setError(null);
                etrePassword.setError(null);
                txInputrePassword.setErrorEnabled(false);
                txInputPassword.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPhone.setError(null);

                txInputPhone.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void attemptLogin(String name, int country , int city , String email , String phone , String password){


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", name)
                        .addFormDataPart("country_id", String.valueOf(country))
                        .addFormDataPart("city_id", String.valueOf(city))
                        .addFormDataPart("email", email)
                        .addFormDataPart("location", addressData.getLatitude() +","+ addressData.getLongitude())
                        .addFormDataPart("phone", phone)
                        .addFormDataPart("password", password)
                        .addFormDataPart("password_confirmation", password)
                        .build();
                Call<UserRegisterResponse> call_login = service.Register(
                        requestBody
                );
                call_login.enqueue(new Callback<UserRegisterResponse>() {
                    @Override
                    public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Log.d("REG", "onResponse: "+response.body().getData().getObject().getEmail());
                                //TODO POJO USER INFO
                                //TODO POJO USER INFO
                                if(!ServiesSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                                    ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData().getObject());
                                }
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
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
                    public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
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
    private boolean getRating(String password) throws IllegalArgumentException {
        if (password == null) {throw new IllegalArgumentException();}
        if (password.length() < 8) {
            txInputrePassword.setError(getResources().getString(R.string.error_short));
            return false;
        } // minimal pw length of 6
        if (password.toLowerCase().equals(password)) {
            txInputrePassword.setError(getResources().getString(R.string.please_use_atleast_one_uppercase_letter));
            return false;
        } // lower and upper case
        int numDigits= Utilites.getNumberDigits(password);
        if (numDigits > 0 && numDigits == password.length()) {
            txInputrePassword.setError(getResources().getString(R.string.please_enter_valid_password));
            return false;
        } // contains digits and non-digits
        return true;
    }
    private boolean validatePass() {


        check = etPassword.getText().toString();

        if (check.length() < 6 || check.length() > 20) {
            return false;
        } else if (!check.matches("^[A-za-z0-9@]+")) {
            return false;
        }
        return true;
    }

    private boolean validateEmail() {

        check = etEmail.getText().toString();

        if (check.length() < 8 || check.length() > 40) {
            return false;
        } else if (!check.matches("^[A-za-z0-9.@]+")) {
            return false;
        } else if (!check.contains("@") || !check.contains(".")) {
            return false;
        }

        return true;
    }
    private void placePicker() {
        Intent intent = new PlacePicker.IntentBuilder()
                .setGoogleMapApiKey(getResources().getString(R.string.api_key))
                .setLatLong(18.520430, 73.856743)
                .setMapZoom(19.0f)
                .setAddressRequired(true)
                .setFabColor(R.color.primaryColorYellowBlack)
                .setPrimaryTextColor(R.color.black)
                .build(this);
        startActivityForResult(intent, Constants.PLACE_PICKER_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData =  data.getParcelableExtra(Constants.ADDRESS_INTENT);
                addressData.getAddressList();
                setAddressData(addressData);
                Location mLocation = new Location("");
                mLocation.setLatitude(addressData.getLatitude());
                mLocation.setLongitude(addressData.getLongitude());
                init(getAddressData());
                //   getCurrentAddress(mLocation);

            }
        }
    }

    private void init(AddressData _addressData) {
        Log.d("ADDRESS", "init: PlaceName " + addressData.getPlaceName());
        Log.d("ADDRESS", "init: Latitude " + addressData.getLatitude());
        Log.d("ADDRESS", "init: LongLatitude " + addressData.getLongitude());

    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }

    private void getMainCategory() {
        ArrayList<String> Values_main = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<CountryResponse> call = service.getAllCountries();

        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

                ArrayList<Country> jsonresponse  = new ArrayList<>(response.body().getData());
                for (int i = 0; i <jsonresponse.size() ; i++) {
                    Values_main.add(jsonresponse.get(i).getName());
                }
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateCustomerDetailsActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_main);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_main_ip.setAdapter(spinnerMainAdapter);
                spinner_main_ip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        main_ip.setText(spinner_main_ip.getSelectedItem().toString());
                        main_ip.dismissDropDown();
                        //int retval=jsonresponse.indexOf(spinner_main_ip.getSelectedItem().toString());

                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(spinner_main_ip.getSelectedItem().toString())) {
                                // Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getGovernorateName(), Toast.LENGTH_SHORT).show();
                                getSubCategory(jsonresponse.get(i).getId());
                                setGovermentID(jsonresponse.get(i).getId());
                            }
                        }



                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        main_ip.setText(spinner_main_ip.getSelectedItem().toString());
                        main_ip.dismissDropDown();
                    }
                });

            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(CreateCustomerDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }
    private void getSubCategory(int type) {

        List<String> Values_sub = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<CityResponse> call = service.getAllCities(type);

        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                List<City> jsonresponse  = new ArrayList<>(response.body().getData());
                for (int i = 0; i <jsonresponse.size() ; i++) {
                    Values_sub.add(jsonresponse.get(i).getName());
                }
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateCustomerDetailsActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_sub);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_sub_ip.setAdapter(spinnerMainAdapter);

                spinner_sub_ip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sub_ip.setText(spinner_sub_ip.getSelectedItem().toString());
                        sub_ip.dismissDropDown();
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(spinner_sub_ip.getSelectedItem().toString())) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setCityID(jsonresponse.get(i).getId());
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        sub_ip.setText(spinner_sub_ip.getSelectedItem().toString());
                        sub_ip.dismissDropDown();
                    }
                });


            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Toast.makeText(CreateCustomerDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }

    public int getGovermentID() {
        return GovermentID;
    }

    public void setGovermentID(int govermentID) {
        GovermentID = govermentID;
    }

    public int getCityID() {
        return CityID;
    }

    public void setCityID(int cityID) {
        CityID = cityID;
    }
}