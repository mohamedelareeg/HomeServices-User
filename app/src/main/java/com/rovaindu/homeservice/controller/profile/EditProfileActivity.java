package com.rovaindu.homeservice.controller.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.rovaindu.homeservice.auth.CreateAgentSecondActivity;
import com.rovaindu.homeservice.auth.CreateCustomerActivity;
import com.rovaindu.homeservice.auth.CreateCustomerDetailsActivity;
import com.rovaindu.homeservice.controller.map.base.AddressData;
import com.rovaindu.homeservice.manager.ServiesSharedPrefManager;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.City;
import com.rovaindu.homeservice.retrofit.models.Country;
import com.rovaindu.homeservice.retrofit.models.ServiesUser;
import com.rovaindu.homeservice.retrofit.response.CityResponse;
import com.rovaindu.homeservice.retrofit.response.CountryResponse;
import com.rovaindu.homeservice.retrofit.response.UpdateProfileResponse;
import com.rovaindu.homeservice.retrofit.response.UserRegisterResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.Utilites;
import com.rovaindu.homeservice.utils.map.PlacePicker;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, getResources().getString(R.string.permisson_denied), Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private ImageView agentImg;
    private final int GALLERYPIC = 1;
    private Bitmap PICImage;
    private AutoCompleteTextView main_ip;
    private Spinner spinner_main_ip;
    private ArrayAdapter<String> adapter_main;

    private AutoCompleteTextView sub_ip;
    private Spinner spinner_sub_ip;
    private ArrayAdapter<String> adapter_sub;

    private String check;
    private AddressData addressData;
    private RelativeLayout locationPanel;
    private TextInputLayout txFirstName  , txInputEmail  , txInputPhone;
    private TextInputEditText etFirstName  , etEmail   , etPhone;
    private MaterialButton finishButton;
    private int GovermentID = -1 , CityID = -1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.edit_profile));

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
        txInputEmail = findViewById(R.id.txInputEmail);
        txInputPhone = findViewById(R.id.txInputPhone);
        locationPanel = findViewById(R.id.locationPanel);

        main_ip = (AutoCompleteTextView) findViewById(R.id.main_ip);
        sub_ip = (AutoCompleteTextView) findViewById(R.id.sub_ip);

        spinner_main_ip = (Spinner) findViewById(R.id.spinner_main_ip);
        spinner_sub_ip = (Spinner) findViewById(R.id.spinner_sub_ip);

        etFirstName = findViewById(R.id.etFirstName);
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

                            if (getGovermentID() != -1) {
                                if (getCityID() != -1) {
                                    if (addressData != null) {
                                        if (etPhone.getText().length() == 11) {

                                            if (getPICImage() != null) {
                                                Log.d("REG", "onClick: 1 ");
                                                String ImagePic = convertImageToStringForServer(getPICImage());
                                                attemptLogin(etFirstName.getText().toString(), getGovermentID(), getCityID(), etEmail.getText().toString(), etPhone.getText().toString() , ImagePic);
                                            }
                                            else
                                            {
                                                Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_picture_first), Toast.LENGTH_SHORT, true).show();
                                            }
                                        } else {
                                            txInputPhone.setError(getResources().getString(R.string.please_enter_valid_phone));
                                        }
                                    } else {
                                        Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_your_current_location), Toast.LENGTH_SHORT, true).show();
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
        agentImg = findViewById(R.id.agentImg);
        agentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, GALLERYPIC);
            }
        });
    }

    public static String convertImageToStringForServer(Bitmap imageBitmap){
        try {


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (imageBitmap != null) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] byteArray = stream.toByteArray();
                return Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        }
        catch (Exception e)
        {

            Log.d("REG", "convertImageToStringForServer: " + e.getLocalizedMessage());
            return null;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERYPIC) {
            if (resultData != null) {
                Uri contentURI = resultData.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    setPICImage(bitmap);
                    agentImg.setImageBitmap(getPICImage());


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && resultData != null) {
                AddressData addressData =  resultData.getParcelableExtra(Constants.ADDRESS_INTENT);
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

    public Bitmap getPICImage() {
        return PICImage;
    }

    public void setPICImage(Bitmap PICImage) {
        this.PICImage = PICImage;
    }

    private void attemptLogin(String name, int country , int city , String email , String phone   , String image ){

        ServiesUser user = ServiesSharedPrefManager.getInstance(getApplicationContext()).getUser();
        ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , user.getApiToken()).create(ApiInterface.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", name)
                        .addFormDataPart("email", email)
                        .addFormDataPart("city_id", String.valueOf(city))
                        .addFormDataPart("location", addressData.getLatitude() +","+ addressData.getLongitude())
                        .addFormDataPart("phone", phone)
                        .addFormDataPart("image", image)
                        .build();
                Call<UpdateProfileResponse> call_login = service.update_profile(
                        requestBody
                );
                call_login.enqueue(new Callback<UpdateProfileResponse>() {
                    @Override
                    public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {

                        if(response.body() != null) {
                            if (response.body().getErrors().size() > 0) {
                                //etPassword.setError(response.body().getMessage());
                                Log.d("REG", "onResponse: " + response.body().getMessage());
                            } else {
                                Log.d("REG", "onResponse: "+response.body().getData().getEmail());
                                //TODO POJO USER INFO
                                //TODO POJO USER INFO
                                if(!ServiesSharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                                    ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
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
                    public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
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
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(EditProfileActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_main);
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
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(EditProfileActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_sub);
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
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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