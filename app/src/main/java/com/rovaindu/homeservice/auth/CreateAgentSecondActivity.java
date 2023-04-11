package com.rovaindu.homeservice.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.rovaindu.homeservice.retrofit.ApiInterface;
import com.rovaindu.homeservice.retrofit.RetrofitClient;
import com.rovaindu.homeservice.retrofit.models.Bank;
import com.rovaindu.homeservice.retrofit.models.ServiesAgent;
import com.rovaindu.homeservice.retrofit.response.AgentCompleteRegisterResponse;
import com.rovaindu.homeservice.retrofit.response.BankResponse;
import com.rovaindu.homeservice.utils.Constants;
import com.rovaindu.homeservice.utils.views.TextViewAr;
import com.rovaindu.homeservice.utils.views.materialSpinner.MaterialSpinner;
import com.rovaindu.homeservice.utils.views.toasty.Toasty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAgentSecondActivity extends AppCompatActivity {

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
    MaterialSpinner BankSpinner , TBankSpinner;
    private TextInputLayout txAccountNumber , txAccountName , txIBanNumber , txTransferName , txTAccountNumber , txMoney;
    private TextInputEditText etAccountNumber , etAccountName , etIBanNumber , etTransferName , etTAccountNumber , etMoney;
    private MaterialButton finishButton;
    private String check;
    private RelativeLayout DatePanel;
    private int BankID = -1 , TBankID = -1;
    private CheckBox cbTerms;
    private final int GALLERYPIC = 1;
    private ImageView pickerPic;
    final Calendar myCalendar = Calendar.getInstance();
    private TextView current_date;
    private Bitmap PICImage;
    private ServiesAgent agent;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agent_second);

        TextViewAr appname = findViewById(R.id.appname);
        appname.setVisibility(View.VISIBLE);
        appname.setText(getResources().getString(R.string.continue_agent_register));

        // Back Button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        agent = (ServiesAgent) getIntent().getExtras().getSerializable(Constants.BUNDLE_AGENTS_LIST);
        //private TextInputLayout txAccountNumber , txAccountName , txIBanNumber , txTransferName , txTAccountNumber , txMoney;
        //private TextInputEditText etAccountNumber , etAccountName , etIBanNumber , etTransferName , etTAccountNumber , etMoney;
        txAccountNumber = findViewById(R.id.txAccountNumber);
        txAccountName = findViewById(R.id.txAccountName);
        txIBanNumber = findViewById(R.id.txIBanNumber);
        txTransferName = findViewById(R.id.txTransferName);
        txTAccountNumber = findViewById(R.id.txTAccountNumber);
        txMoney = findViewById(R.id.txMoney);
        etAccountNumber = findViewById(R.id.etAccountNumber);
        etAccountName = findViewById(R.id.etAccountName);
        etIBanNumber = findViewById(R.id.etIBanNumber);
        etTransferName = findViewById(R.id.etTransferName);
        etTAccountNumber = findViewById(R.id.etTAccountNumber);
        etMoney = findViewById(R.id.etMoney);

        finishButton = findViewById(R.id.finishButton);
        cbTerms = findViewById(R.id.cbTerms);
        DatePanel = findViewById(R.id.datePanel);
        pickerPic = findViewById(R.id.pickerPic);
        BankSpinner = findViewById(R.id.BankSpinner);
        TBankSpinner = findViewById(R.id.TBankSpinner);
        current_date = findViewById(R.id.current_date);
        getBank();
        pickerPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, GALLERYPIC);
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1 ;
                Log.d("REG", "onDateSet: mm/dd/yyy: " + year + "/" +  + month + "/"+  + day + "/");//Date_style
                String date = month + "/" + day + "/" +year;
                current_date.setText(date);//Show Date_in_TextView

            }
        };
        DatePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(CreateAgentSecondActivity.this,
                        R.style.Theme_AppCompat_Dialog ,//Themes
                        mDateSetListener ,//set Listener_method
                        year,month,day);//set date_arrangment
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//Color
                dialog.show();//Show Date Dialog
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cbTerms.isChecked()) {
                    if(etAccountName.getText().length() > 0) {
                        if(etAccountNumber.getText().length() > 0) {
                            if(etIBanNumber.getText().length() > 0) {
                                if(etTransferName.getText().length() > 0) {
                                    if(etTAccountNumber.getText().length() > 0) {
                                        if(etMoney.getText().length() > 0) {
                                            if (getPICImage() != null) {
                                                try {


                                                    //Sring accountName, String accountNumber , String IBanNumber , String TAccountName , String TAccountNumber , String Money , String Date
                                                    Log.d("REG", "onClick: 1 ");
                                                    String ImagePic = convertImageToStringForServer(getPICImage());

                                                    Log.d("REG", "onClick: 2 ");
                                                    attemptLogin(etAccountName.getText().toString(), etAccountNumber.getText().toString(), etIBanNumber.getText().toString(), etTransferName.getText().toString(), etTAccountNumber.getText().toString(), etMoney.getText().toString(), current_date.getText().toString(), ImagePic);
                                                }
                                                catch (Exception e)
                                                {
                                                    Log.d("REG", "onClick: " + e.getLocalizedMessage());
                                                }
                                            }
                                            else
                                            {
                                                Toasty.error(getApplicationContext(), getResources().getString(R.string.please_select_id_picture_first), Toast.LENGTH_SHORT, true).show();
                                            }

                                        } else {
                                            txMoney.setError(getResources().getString(R.string.please_enter_money));
                                        }
                                    } else {
                                        txTAccountNumber.setError(getResources().getString(R.string.please_enter_t_account_number));
                                    }
                                } else {
                                    txTransferName.setError(getResources().getString(R.string.please_enter_transer_name));
                                }
                            } else {
                                txIBanNumber.setError(getResources().getString(R.string.please_enter_iban_number));
                            }
                        } else {
                            txAccountNumber.setError(getResources().getString(R.string.please_enter_account_number));
                        }
                    }
                    else
                    {
                        txAccountName.setError(getResources().getString(R.string.please_enter_account_name_first));
                    }
                }
                else
                {
                    cbTerms.setError(getResources().getString(R.string.please_accept_30_days_trailer_first));
                }

            }
        });
        etAccountName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etAccountName.setError(null);

                txAccountName.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etAccountNumber.setError(null);

                txAccountNumber.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etIBanNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etIBanNumber.setError(null);

                txIBanNumber.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etMoney.setError(null);

                txMoney.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etTAccountNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etTAccountNumber.setError(null);

                txTAccountNumber.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etTransferName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etTransferName.setError(null);

                txTransferName.setErrorEnabled(false); // disable error


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void attemptLogin(String accountName, String accountNumber , String IBanNumber , String TAccountName , String TAccountNumber , String Money , String Date , String image){

        try {


            ApiInterface service = RetrofitClient.retrofitAPIWrite("ar" , agent.getApiToken()).create(ApiInterface.class);

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("account_number", accountNumber)
                            .addFormDataPart("account_name", accountName)
                            .addFormDataPart("bank_id", String.valueOf(getBankID()))
                            .addFormDataPart("iban", IBanNumber)
                            .addFormDataPart("sender", TAccountName)
                            .addFormDataPart("sender_account_number", TAccountNumber)
                            .addFormDataPart("sender_bank_id", String.valueOf(getTBankID()))
                            .addFormDataPart("ammount", Money)
                            .addFormDataPart("date", Date)
                            .addFormDataPart("image", image)
                            .addFormDataPart("trail", String.valueOf(1))

                            .build();
                    Call<AgentCompleteRegisterResponse> call_login = service.CompleteRegisterAgent(
                            requestBody
                    );
                    call_login.enqueue(new Callback<AgentCompleteRegisterResponse>() {
                        @Override
                        public void onResponse(Call<AgentCompleteRegisterResponse> call, Response<AgentCompleteRegisterResponse> response) {

                            if (response.body() != null) {
                                if (response.body().getErrors().size() > 0) {
                                    //etPassword.setError(response.body().getMessage());
                                    Log.d("REG", "onResponse: " + response.body().getMessage());
                                    Log.d("REG", "onResponse: ");
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
                                    ServiesSharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData().getObject());
                                }


                                 */
                                    //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    Toast.makeText(CreateAgentSecondActivity.this, getResources().getText(R.string.register_succ), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d("REG", "onResponse: " + response.code());
                                Log.d("REG", "onResponse: " + response.message());
                                Log.d("REG", "onResponse: " + response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<AgentCompleteRegisterResponse> call, Throwable t) {
                            Log.d("REG", "onFailure: " + t.getLocalizedMessage());
                            //txAccountName.setError(getResources().getString(R.string.invild_email_or_password));
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

    private void updateDate() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        current_date.setText(sdf.format(myCalendar.getTime().toString()));
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
    private void getBank() {

        List<String> Values_sub = new ArrayList<>();


        ApiInterface service = RetrofitClient.retrofitWrite("ar").create(ApiInterface.class);


        Call<BankResponse> call = service.getAllBanks();

        call.enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {

                List<Bank> jsonresponse  = new ArrayList<>(response.body().getData());
                for (int i = 0; i <jsonresponse.size() ; i++) {
                    Values_sub.add(jsonresponse.get(i).getName().toString());
                }
                ArrayAdapter<String> spinnerMainAdapter = new ArrayAdapter<String>(CreateAgentSecondActivity.this,  android.R.layout.simple_dropdown_item_1line, Values_sub);
                spinnerMainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                BankSpinner.setItems(Values_sub);
                TBankSpinner.setItems(Values_sub);
                setBankID(jsonresponse.get(0).getId());
                BankSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            String month = jsonresponse.get(i).getName();
                            if (month.equals(item)) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setBankID(jsonresponse.get(i).getId());
                                Log.d("REG", "onItemSelected: " + jsonresponse.get(i).getId());
                            }
                        }
                    }
                });

                TBankSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                    @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        for (int i = 0; i < jsonresponse.size(); i++) {
                            String month = jsonresponse.get(i).getName();
                            if (month.equals(item)) {
                                //Toast.makeText(AddTeacherActivity.this, "" + jsonresponse.get(i).getId() + "  " + jsonresponse.get(i).getCityName(), Toast.LENGTH_SHORT).show();
                                //getGroupCategory(jsonresponse.get(i).getId());
                                setTBankID(jsonresponse.get(i).getId());
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
            public void onFailure(Call<BankResponse> call, Throwable t) {
                Toast.makeText(CreateAgentSecondActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




    }

    public int getBankID() {
        return BankID;
    }

    public void setBankID(int bankID) {
        BankID = bankID;
    }

    public int getTBankID() {
        return TBankID;
    }

    public void setTBankID(int TBankID) {
        this.TBankID = TBankID;
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
        if (requestCode == GALLERYPIC) {
            if (resultData != null) {
                Uri contentURI = resultData.getData();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    setPICImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CreateAgentSecondActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}