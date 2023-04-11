package com.rovaindu.homeservice.auth;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.rovaindu.homeservice.R;
import com.rovaindu.homeservice.base.BaseActivity;
import com.rovaindu.homeservice.controller.map.base.AddressData;
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.City;
import com.rovaindu.homeservice.retrofit.models.Country;
import com.rovaindu.homeservice.retrofit.models.Job;
import com.rovaindu.homeservice.retrofit.models.Plan;
import com.rovaindu.homeservice.retrofit.models.Service;
import com.rovaindu.homeservice.retrofit.response.AgentRegisterResponse;
import com.rovaindu.homeservice.retrofit.response.CityResponse;
import com.rovaindu.homeservice.retrofit.response.CountryResponse;
import com.rovaindu.homeservice.retrofit.response.JobResponse;
import com.rovaindu.homeservice.retrofit.response.PlanResponse;
import com.rovaindu.homeservice.retrofit.response.ServiesResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.Utilites;
import com.rovaindu.homeservice.utils.map.PlacePicker;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.materialSpinner.MaterialSpinner;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAgentFirstActivity extends BaseActivity {


    MaterialSpinner planSpinner , countrySpinner , citySpinner, jobSpinner , serviesSpinner , sallerySpinner;
    private String check;
    private AddressData addressData;
    private RelativeLayout locationPanel;
    private TextInputLayout txFirstName , txInputPassword , txInputrePassword , txInputEmail  , txInputPhone , txInputIDNumber , txInputExperience , txInputExperienceYears;
    private TextInputEditText etFirstName , etPassword , etrePassword , etEmail   , etPhone , etIDNumber , etExperience , etExperiencYears;
    private MaterialButton finishButton;
    private int GovermentID = -1 , CityID = -1 , PlanID = -1 , JobID = -1 , ServiesID = -1 , SalleryID = -1 ;
    private CheckBox cbTerms;
    private Bitmap IDImage , PICImage;
    private ImageView pickerPic , pickerID;
    private final int GALLERYPIC = 1;
    private final int GALLERYID = 2;
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
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agent_first);
        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.new_agent_register));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        pickerPic = findViewById(R.id.pickerPic);
        pickerID = findViewById(R.id.pickerID);
        cbTerms = findViewById(R.id.cbTerms);
        txFirstName = findViewById(R.id.txFirstName);
        txInputPassword = findViewById(R.id.txInputPassword);
        txInputrePassword = findViewById(R.id.txInputrePassword);
        txInputEmail = findViewById(R.id.txInputEmail);
        txInputPhone = findViewById(R.id.txInputPhone);
        locationPanel = findViewById(R.id.locationPanel);
        //txInputIDNumber , txInputExperience , txInputExperienceYears etIDNumber , etExperience , etExperiencYears
        //planSpinner , countrySpinner , citySpinner, jobSpinner , serviesSpinner , sallerySpinner;
        txInputIDNumber = findViewById(R.id.txInputIDNumber);
        txInputExperience = findViewById(R.id.txInputExperience);
        txInputExperienceYears = findViewById(R.id.txInputExperienceYears);
        etIDNumber = findViewById(R.id.etIDNumber);
        etExperience = findViewById(R.id.etExperience);
        etExperiencYears = findViewById(R.id.etExperiencYears);


        planSpinner = findViewById(R.id.planSpinner);
        countrySpinner = findViewById(R.id.countrySpinner);
        citySpinner = findViewById(R.id.citySpinner);
        jobSpinner = findViewById(R.id.jobSpinner);
        serviesSpinner = findViewById(R.id.serviesSpinner);
        sallerySpinner = findViewById(R.id.sallerySpinner);


        etFirstName = findViewById(R.id.etFirstName);
        etPassword = findViewById(R.id.etPassword);
        etrePassword = findViewById(R.id.etrePassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);

        finishButton = findViewById(R.id.signup_next_button);
        getMainCategory();
        getPlan();
        getJob();
        getSallery();

        pickerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(galleryIntent, GALLERYPIC);
                    }
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, GALLERYPIC);
                }

            }
        });

        pickerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(galleryIntent, GALLERYID);
                    }
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(galleryIntent, GALLERYID);
                }

            }
        });
        locationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placePicker();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //encode image to base64 string
                /*
                try {


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.google_play);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    //decode base64 string to image
                    imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    //image.setImageBitmap(decodedImage);
                }
                catch (Exception e)
                {
                    Log.d("REG", "onClick: "  + e.getLocalizedMessage());
                }

                 */
                if(cbTerms.isChecked()) {
                if(etFirstName.getText().length() > 0) {
                    if (isEmailValid(etEmail.getText().toString())) {
                        if (getRating(etPassword.getText().toString())) {
                            if (getGovermentID() != -1) {
                                if (getCityID() != -1) {
                                    if (addressData != null) {
                                        if (etPhone.getText().length() == 11) {
                                            if (etPassword.getText().toString().equals(etrePassword.getText().toString())) {

                                                if(getPlanID() != -1 & getJobID() != -1 && getServiesID() != -1) {
                                                    if(etIDNumber.getText().length() > 0) {
                                                        Log.d("REG", "onClick: 1 ");

                                                        Log.d("REG", "onClick: 2 ");
                                                        if (getPICImage() != null) {
                                                            if (getIDImage() != null) {
                                                                String ImagePic = convertImageToStringForServer(getPICImage());
                                                                String ImageID = convertImageToStringForServer(getIDImage());
                                                                attemptLogin(etFirstName.getText().toString(), getGovermentID(), getCityID(), etEmail.getText().toString(), etPhone.getText().toString(), etPassword.getText().toString(), etIDNumber.getText().toString(), etExperience.getText().toString(), etExperiencYears.getText().toString(), ImagePic , ImageID);
                                                            }
                                                            else
                                                            {
                                                                Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_id_picture_first), Toast.LENGTH_SHORT, true).show();
                                                            }
                                                             }
                                                        else
                                                        {
                                                            Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_profile_picture_first), Toast.LENGTH_SHORT, true).show();
                                                        }
                                                    }
                                                    else
                                                        txInputIDNumber.setError(getResources().getString(R.string.please_enter_id_number));
                                                }
                                                else
                                                {
                                                    Log.d("REG", "onClick: " + getPlanID() + getJobID() + getServiesID());
                                                }
                                            } else
                                                txInputrePassword.setError(getResources().getString(R.string.password_doesnt_match));
                                        } else {
                                            txInputPhone.setError(getResources().getString(R.string.please_enter_valid_phone));
                                        }
                                    } else {
                                        Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_your_current_location), Toast.LENGTH_SHORT, true).show();
                                    }
                                }
                                else
                                {
                                    Log.d("REG", "onClick: city " );
                                }
                            }
                            else
                            {
                                Log.d("REG", "onClick: Country " );
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
                else
                {
                    cbTerms.setError(getResources().getString(R.string.plase_agree_terms_conditions_first));
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

    private void getSallery() {
        // Array of choices
        Integer sallery[] = {10,20,30,40,50, 60,70,80,90,100,110,120,130,140,150,160,170,180,190,200};


        // Application of the Array to the Spinner
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(getApplicationContext(),   android.R.layout.simple_spinner_item, sallery);
        sallerySpinner.setItems(sallery);
        sallerySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                setSalleryID(Integer.parseInt(item.toString()));
            }
        });
        /*
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_sallery_ip.setAdapter(spinnerArrayAdapter);

         */
    }

    private void attemptLogin(String name, int country , int city , String email , String phone , String password , String idnumber , String expirence , String expirenceYears , String imagePic  ,String imageID){

        try {


            ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("plan_id", String.valueOf(getPlanID()))
                            .addFormDataPart("name", name)
                            .addFormDataPart("identity_number", idnumber)
                            .addFormDataPart("country_id", String.valueOf(country))
                            .addFormDataPart("city_id", String.valueOf(city))
                            .addFormDataPart("email", email)
                            .addFormDataPart("location", addressData.getLatitude() + "," + addressData.getLongitude())
                            .addFormDataPart("phone", phone)
                            .addFormDataPart("job_id", String.valueOf(getJobID()))
                            .addFormDataPart("services[]", String.valueOf(getServiesID()))
                            .addFormDataPart("experience", expirence)
                            .addFormDataPart("experience_years", expirenceYears)
                            .addFormDataPart("hourly_wage", String.valueOf(getSalleryID()))
                            .addFormDataPart("image", imagePic)
                            .addFormDataPart("identity_image", imageID)
                            .addFormDataPart("password", password)
                            .addFormDataPart("password_confirmation", password)
                            .build();
                    Call<AgentRegisterResponse> call_login = service.RegisterAgent(
                            requestBody
                    );
                    call_login.enqueue(new Callback<AgentRegisterResponse>() {
                        @Override
                        public void onResponse(Call<AgentRegisterResponse> call, Response<AgentRegisterResponse> response) {
                            Log.d("REG", "onResponse: 77");
                            if (response.body() != null) {
                                if (response.body().getErrors().size() > 0) {
                                    //etPassword.setError(response.body().getMessage());
                                    Log.d("REG", "onResponse: " + response.body().getMessage());
                                } else {
                                    Log.d("REG", "onClick: Success ");
                                    //TODO POJO USER INFO
                                    //TODO POJO USER INFO
                                /*
                                if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

                                    UserAddress addressData = new UserAddress(37.42199845544925, -122.0839998498559, "Mountain View, CA 94043");
                                    //int id, String name, String email, String thumb_image, String gender, String country, String city, String phone, String gcmtoken, UserAddress userAddress
                                    User user = new User(1, "محمد السيد", "mohaa.coder@yahoo.com",
                                            "https://www.mantruckandbus.com/fileadmin/media/bilder/02_19/219_05_busbusiness_interviewHeader_1485x1254.jpg", "رجل"
                                            , "المملكة العربية السعودية", "الرياض", "1277637646", "", addressData);


                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                                    ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData().getObject());
                                }



                                 */
                                try {


                                    /*
                                    ServiesAgentSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData());
                                    startActivity(new Intent(CreateAgentFirstActivity.this, CreateAgentSecondActivity.class));

                                     */
                                    Intent loginIntent = new Intent(CreateAgentFirstActivity.this, CreateAgentSecondActivity.class);
                                    loginIntent.putExtra(Constants.BUNDLE_AGENTS_LIST, (Serializable) response.body().getData());
                                    startActivity(loginIntent);

                                }
                                catch (Exception e)
                                {
                                    Log.d("REG", "onResponse: " + e.getLocalizedMessage());
                                }
                                }

                            } else {
                                Log.d("REG", "onResponse: " + response.code());
                                Log.d("REG", "onResponse: " + response.message());
                                Log.d("REG", "onResponse: " + response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<AgentRegisterResponse> call, Throwable t) {
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
        catch (Exception e)
        {
            Log.d("REG", "attemptLogin: " + e.getLocalizedMessage());
        }

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
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentFirstActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_main);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                countrySpinner.setItems(Values_main);
                getSubCategory(jsonresponse.get(0).getId());
                setGovermentID(jsonresponse.get(0).getId());
                countrySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(item)) {
                                // Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getGovernorateName(), Toast.LENGTH_SHORT).show();
                                getSubCategory(jsonresponse.get(i).getId());
                                setGovermentID(jsonresponse.get(i).getId());
                            }
                        }
                    }
                });
                /*
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

                 */

            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                Toast.makeText(CreateAgentFirstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentFirstActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_sub);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                citySpinner.setItems(Values_sub);
                setCityID(jsonresponse.get(0).getId());
                citySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(item)) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setCityID(jsonresponse.get(i).getId());
                            }
                        }
                    }
                });
                /*
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

                 */


            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
                Toast.makeText(CreateAgentFirstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }

    private void getPlan() {

        List<String> Values_sub = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<PlanResponse> call = service.getAllPlans();

        call.enqueue(new Callback<PlanResponse>() {
            @Override
            public void onResponse(Call<PlanResponse> call, Response<PlanResponse> response) {

                List<Plan> jsonresponse  = new ArrayList<>(response.body().getData());
                for (int i = 0; i <jsonresponse.size() ; i++) {
                    Values_sub.add(jsonresponse.get(i).getMonthCount().toString() + " " + getResources().getString(R.string.months));
                }
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentFirstActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_sub);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                planSpinner.setItems(Values_sub);
                setPlanID(jsonresponse.get(0).getId());
                planSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            String month = jsonresponse.get(i).getMonthCount() + " " + getResources().getString(R.string.months);
                            if (month.equals(item)) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setPlanID(jsonresponse.get(i).getId());
                                Log.d("REG", "onItemSelected: " + jsonresponse.get(i).getId());
                            }
                        }
                    }
                });
                /*
                spinner_plan_ip.setAdapter(spinnerMainAdapter);

                spinner_plan_ip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        plan_ip.setText(spinner_plan_ip.getSelectedItem().toString());
                        plan_ip.dismissDropDown();
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getMonthCount().equals(spinner_plan_ip.getSelectedItem().toString())) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setPlanID(jsonresponse.get(i).getId());
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        plan_ip.setText(spinner_plan_ip.getSelectedItem().toString());
                        plan_ip.dismissDropDown();
                    }
                });


                 */

            }

            @Override
            public void onFailure(Call<PlanResponse> call, Throwable t) {
                Toast.makeText(CreateAgentFirstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }
    private void getJob() {
        ArrayList<String> Values_main = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<JobResponse> call = service.getAllJobs();

        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {

                ArrayList<Job> jsonresponse  = new ArrayList<>(response.body().getData());
                for (int i = 0; i <jsonresponse.size() ; i++) {
                    Values_main.add(jsonresponse.get(i).getName());
                }
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentFirstActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_main);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                jobSpinner.setItems(Values_main);
                getServies(jsonresponse.get(0).getId());
                setJobID(jsonresponse.get(0).getId());
                jobSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(item)) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                getServies(jsonresponse.get(i).getId());
                                setJobID(jsonresponse.get(i).getId());
                            }
                        }
                    }
                });
                /*
                spinner_job_ip.setAdapter(spinnerMainAdapter);
                spinner_job_ip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        job_ip.setText(spinner_job_ip.getSelectedItem().toString());
                        job_ip.dismissDropDown();
                        //int retval=jsonresponse.indexOf(spinner_main_ip.getSelectedItem().toString());

                        for (int i = 0; i < jsonresponse.size(); i++) {
                            if (jsonresponse.get(i).getName().equals(spinner_job_ip.getSelectedItem().toString())) {
                                // Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getGovernorateName(), Toast.LENGTH_SHORT).show();
                                getServies(jsonresponse.get(i).getId());
                                setJobID(jsonresponse.get(i).getId());
                            }
                        }



                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        job_ip.setText(spinner_job_ip.getSelectedItem().toString());
                        job_ip.dismissDropDown();
                    }
                });

                 */

            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(CreateAgentFirstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }
    private void getServies(int type) {

        List<String> Values_sub = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<ServiesResponse> call = service.getAllServies(type);

        call.enqueue(new Callback<ServiesResponse>() {
            @Override
            public void onResponse(Call<ServiesResponse> call, Response<ServiesResponse> response) {

                if(response.body().getData().size() > 0) {
                    List<Service> jsonresponse = new ArrayList<>(response.body().getData());
                    for (int i = 0; i < jsonresponse.size(); i++) {
                        Values_sub.add(jsonresponse.get(i).getName());
                    }
                    ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentFirstActivity.this, android.R.layout.simple_dropdown_item_1line, Values_sub);
                    spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    serviesSpinner.setItems(Values_sub);
                    setServiesID(jsonresponse.get(0).getId());
                    serviesSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            for (int i = 0; i < jsonresponse.size(); i++) {
                                if (jsonresponse.get(i).getName().equals(item)) {
                                    //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                    //getGroupCategory(jsonresponse.get(i).getId());
                                    setServiesID(jsonresponse.get(i).getId());
                                }
                            }
                        }
                    });
                    /*
                    spinner_servies_ip.setAdapter(spinnerMainAdapter);

                    spinner_servies_ip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            servies_ip.setText(spinner_servies_ip.getSelectedItem().toString());
                            servies_ip.dismissDropDown();
                            for (int i = 0; i < jsonresponse.size(); i++) {
                                if (jsonresponse.get(i).getName().equals(spinner_servies_ip.getSelectedItem().toString())) {
                                    //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                    //getGroupCategory(jsonresponse.get(i).getId());
                                    setServiesID(jsonresponse.get(i).getId());
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            servies_ip.setText(spinner_servies_ip.getSelectedItem().toString());
                            servies_ip.dismissDropDown();
                        }
                    });

                     */
                }
                else
                {
                    setServiesID(-1);
                }

            }

            @Override
            public void onFailure(Call<ServiesResponse> call, Throwable t) {
                Toast.makeText(CreateAgentFirstActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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

    public int getPlanID() {
        return PlanID;
    }

    public void setPlanID(int planID) {
        PlanID = planID;
    }

    public int getJobID() {
        return JobID;
    }

    public void setJobID(int jobID) {
        JobID = jobID;
    }

    public int getServiesID() {
        return ServiesID;
    }

    public void setServiesID(int serviesID) {
        ServiesID = serviesID;
    }

    public int getSalleryID() {
        return SalleryID;
    }

    public void setSalleryID(int salleryID) {
        SalleryID = salleryID;
    }

    public Bitmap getIDImage() {
        return IDImage;
    }

    public void setIDImage(Bitmap IDImage) {
        this.IDImage = IDImage;
    }


    public Bitmap getPICImage() {
        return PICImage;
    }

    public void setPICImage(Bitmap PICImage) {
        this.PICImage = PICImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == this.RESULT_CANCELED) {
            return;
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


        if (requestCode == GALLERYID) {
            if (resultData != null) {
                Uri contentURI = resultData.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    setIDImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateAgentFirstActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        if (requestCode == GALLERYPIC) {
            if (resultData != null) {
                Uri contentURI = resultData.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    setPICImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateAgentFirstActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}