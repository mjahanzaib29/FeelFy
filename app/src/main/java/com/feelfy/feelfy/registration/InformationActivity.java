package com.feelfy.feelfy.registration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.description.AdvancedDiscriptionActivity;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.feelfy.feelfy.utils.KeyboardUtils;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationActivity extends AppCompatActivity {

    ImageView image_male, image_fmale, image_couple, image_male_to_male, image_women_to_women, iv_back;
    Context mContext;
    LinearLayout ll_advanced_discription;
    TextView tv_next;
    EditText edt_date_birth,et_name,et_gender_identify,et_sexual_orientation;
    DatePickerDialog picker_date;
    String title;
   RequestProfileUpdate requestProfileUpdate ;;
   ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mContext = InformationActivity.this;
        //requestProfileUpdate=LoginActivity.requestProfileUpdate;
        Intent intent=getIntent();
        if(intent!=null) {
            requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
        }

        requestProfileUpdate = AppPreferences.loadSharedPreferencesLogList(mContext);
        get_preference_two(Integer.parseInt(requestProfileUpdate.getUser_id()));

        findId();

        title = AppPreferences.loadPreferences(mContext, "TITLE");

        requestProfileUpdate.setYou(title);
        switch (title) {
            case "Male":
                image_male.setVisibility(View.VISIBLE);
                image_fmale.setVisibility(View.GONE);
                image_couple.setVisibility(View.GONE);
                image_male_to_male.setVisibility(View.GONE);
                image_women_to_women.setVisibility(View.GONE);
                break;
            case "Female":
                image_male.setVisibility(View.GONE);
                image_fmale.setVisibility(View.VISIBLE);
                image_couple.setVisibility(View.GONE);
                image_male_to_male.setVisibility(View.GONE);
                image_women_to_women.setVisibility(View.GONE);
                break;
            case "Couple":
                image_male.setVisibility(View.GONE);
                image_fmale.setVisibility(View.GONE);
                image_couple.setVisibility(View.VISIBLE);
                image_male_to_male.setVisibility(View.GONE);
                image_women_to_women.setVisibility(View.GONE);
                break;
            case "Male_to_Male":
                image_male.setVisibility(View.GONE);
                image_fmale.setVisibility(View.GONE);
                image_couple.setVisibility(View.GONE);
                image_male_to_male.setVisibility(View.VISIBLE);
                image_women_to_women.setVisibility(View.GONE);
                break;
            case "Female_to_Female":
                image_male.setVisibility(View.GONE);
                image_fmale.setVisibility(View.GONE);
                image_couple.setVisibility(View.GONE);
                image_male_to_male.setVisibility(View.GONE);
                image_women_to_women.setVisibility(View.VISIBLE);
                break;
        }

        onclick();
    }

    private void findId() {
        image_male = findViewById(R.id.image_male);
        image_fmale = findViewById(R.id.image_fmale);
        image_couple = findViewById(R.id.image_couple);
        image_male_to_male = findViewById(R.id.image_male_to_male);
        image_women_to_women = findViewById(R.id.image_women_to_women);
        ll_advanced_discription = findViewById(R.id.ll_advanced_discription);
        et_name = findViewById(R.id.et_name);
        edt_date_birth = findViewById(R.id.edt_date_birth);
        et_gender_identify = findViewById(R.id.et_gender_identify);
        et_sexual_orientation = findViewById(R.id.et_sexual_orientation);
        tv_next = findViewById(R.id.tv_next);
        iv_back = findViewById(R.id.iv_back);

    }
    private void onclick() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=et_name.getText().toString();
                String Dob=edt_date_birth.getText().toString();
                String sexualo=et_sexual_orientation.getText().toString();
                String genderI=et_gender_identify.getText().toString();

                if(name.equalsIgnoreCase("")){
                    et_name.setError("enter name");
                    et_name.requestFocus();
                }else if(Dob.equalsIgnoreCase("")){
                    edt_date_birth.setError("enter dob");
                    edt_date_birth.requestFocus();
                }else if(sexualo.equalsIgnoreCase("")){
                    et_sexual_orientation.setError("enter sexual orientation");
                    et_sexual_orientation.requestFocus();
                }else if(genderI.equalsIgnoreCase("")){
                    et_gender_identify.setError("enter gender identify");
                    et_gender_identify.requestFocus();
                }else{
                    requestProfileUpdate.setName(name);
                    requestProfileUpdate.setDob(Dob);
                    requestProfileUpdate.setFsexString(sexualo);
                    requestProfileUpdate.setSexual(sexualo);
                    requestProfileUpdate.setGender_identify(genderI);

                    int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
                    String nickname = requestProfileUpdate.getName();
                    String u_Dob = requestProfileUpdate.getDob();
                    String u_orientation = requestProfileUpdate.getSexual();
                    String u_gender = requestProfileUpdate.getGender_identify();

                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> requestProfileUpdateCall = apiInterface.get_preference_two(u_id,nickname,u_Dob,u_orientation,u_gender);
                    requestProfileUpdateCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.v("Mesagecheck", response.message());
                            Toast.makeText(mContext, "Profile Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, FancyFeelingActivity.class);
                            intent.putExtra("model",requestProfileUpdate);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Not Updated", Toast.LENGTH_SHORT).show();

                        }
                    });

                 }
            }
        });
        ll_advanced_discription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AdvancedDiscriptionActivity.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
                finish();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AdvancedDiscriptionActivity.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
            }
        });
        edt_date_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.dismissKeyboard(InformationActivity.this);
                final Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int MONTHS = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                // ===================== date picker dialog ==================
                picker_date = new DatePickerDialog(mContext, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year1, int month, int day1) {
                                edt_date_birth.setText(day1 + "/" + (month + 1) + "/" + year1);
                            }
                        }, year, MONTHS, day);
                picker_date.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker_date.show();
            }
        });
    }

    public void get_preference_two(int user_id){
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.get_user_preference_two(user_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String restwo = response.body().toString();
                try {

                        JSONObject jsonObject = new JSONObject(restwo);
                        JSONArray jsonArray = jsonObject.getJSONArray("two");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if(jsonObject1.getString("nickname").equals("null")){
                                et_name.setText("");
                            }else{
                                et_name.setText(jsonObject1.getString("nickname"));
                            }
                            if(jsonObject1.getString("gender").equals("null")){
                                et_gender_identify.setText("");
                            }else{
                                et_gender_identify.setText(jsonObject1.getString("gender"));
                            }
                            if(jsonObject1.getString("sexualOrientation").equals("null")){
                                et_sexual_orientation.setText("");
                            }else{
                                et_sexual_orientation.setText(jsonObject1.getString("sexualOrientation"));
                            }
                            if(jsonObject1.getString("dob").equals("null")){
                                edt_date_birth.setText("");
                            }else{
                                edt_date_birth.setText(jsonObject1.getString("dob"));
                            }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
