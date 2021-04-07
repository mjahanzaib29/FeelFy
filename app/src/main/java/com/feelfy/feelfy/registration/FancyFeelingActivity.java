package com.feelfy.feelfy.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FancyFeelingActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView image_male, image_female, image_couple, image_men_to_men, image_women_to_women, image_back;
    Context mContext;
    TextView tv_next;
    Intent intent;
    String title;
    RequestProfileUpdate requestProfileUpdate;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_feeling);
        mContext = FancyFeelingActivity.this;
       // requestProfileUpdate=LoginActivity.requestProfileUpdate;

        Intent intent=getIntent();
        if(intent!=null) {
            requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
        }
        get_preference_three(Integer.parseInt(requestProfileUpdate.getUser_id()));
        image_male = findViewById(R.id.image_male);
        image_female = findViewById(R.id.image_female);
        image_couple = findViewById(R.id.image_couple);
        image_men_to_men = findViewById(R.id.image_men_to_men);
        image_women_to_women = findViewById(R.id.image_women_to_women);
        image_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);

        tv_next.setOnClickListener(this);
        image_male.setOnClickListener(this);
        image_female.setOnClickListener(this);
        image_couple.setOnClickListener(this);
        image_men_to_men.setOnClickListener(this);
        image_women_to_women.setOnClickListener(this);
        image_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_male:
                AppPreferences.savePreferences(mContext, "TITLE", "Male");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setFeeling("Male");
                break;
            case R.id.image_female:
                AppPreferences.savePreferences(mContext, "TITLE", "Female");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setFeeling("Female");
                break;
            case R.id.image_couple:
                AppPreferences.savePreferences(mContext, "TITLE", "Couple");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setFeeling("Couple");
                break;
            case R.id.image_men_to_men:
                AppPreferences.savePreferences(mContext, "TITLE", "Male_to_Male");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setFeeling("Male to Male");
                break;
            case R.id.image_women_to_women:
                AppPreferences.savePreferences(mContext, "TITLE", "Female_to_Female");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setFeeling("Female to Female");
                break;
            case R.id.tv_next:

                title = AppPreferences.loadPreferences(mContext, "TITLE");
                if (title.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please Select Status", Toast.LENGTH_SHORT).show();
                } else {
                    String preference = requestProfileUpdate.getFeeling();
                    int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());

                    apiInterface = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponseBody> call = apiInterface.post_fancyfeeling(u_id,preference);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                    intent = new Intent(mContext, RefineActivity.class);
//                    Log.v("gender", requestProfileUpdate.getFeeling());
                    intent.putExtra("model",requestProfileUpdate);
                    mContext.startActivity(intent);
                    finish();
                }
                break;
            case R.id.iv_back:
                intent = new Intent(mContext, InformationActivity.class);
                mContext.startActivity(intent);
//                finish();
                break;
        }
    }

    public void get_preference_three(int user_id){
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.get_user_preference_three(user_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String restwo = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(restwo);
                    JSONArray jsonArray = jsonObject.getJSONArray("three");
                    for (int i =0 ; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String gender = jsonObject1.getString("sexualPreference");
                        Log.v("gender",gender);
                        if(gender.equals("Male")){
                            image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                        }else if(gender.equals("Female")){
                            image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                        }else if(gender.equals("Couple")){
                            image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                        }else if(gender.equals("Men to Men")){
                            image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                        }else if(gender.equals("Female to Female")){
                            image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                        }else{

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
