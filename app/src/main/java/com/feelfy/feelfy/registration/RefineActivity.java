package com.feelfy.feelfy.registration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.description.AdavancedSearch;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefineActivity extends AppCompatActivity {


    SeekBar seekBar, seekBar2;
    TextView tv_range, tv_distance, tv_next;
    ImageView iv_back;
    LinearLayout ll_advanced_search;
    Context mContext;
    ImageView image_male, image_fmale, image_couple, image_male_to_male, image_women_to_women;
    RequestProfileUpdate requestProfileUpdate;
    CrystalRangeSeekbar rangeSeekbar3,rangeSeekbar1;
    EditText ysex,ygender;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine);
        mContext = RefineActivity.this;
        //requestProfileUpdate=LoginActivity.requestProfileUpdate;

        findId();

        Intent intent=getIntent();
        if(intent!=null) {
            requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
        }

        String title = AppPreferences.loadPreferences(mContext, "TITLE");
        requestProfileUpdate.setFeeling(title);
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

        rangeSeekbar3.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number number, Number number1) {
                String min= String.valueOf(number);
                String max= String.valueOf(number1);
                tv_range.setText(min+"-"+max);
                requestProfileUpdate.setFminsearchage(min);
                requestProfileUpdate.setFmaxsearchage(max);
            }
        });

        rangeSeekbar1.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number number2, Number number3) {
                String min1= String.valueOf(number2);
                String max1= String.valueOf(number3);
                tv_distance.setText(min1+"-"+max1);
                requestProfileUpdate.setFmindisttance(min1);
                requestProfileUpdate.setFmaxdistance(max1);
            }
        });
        Onclick();

    }//==================== End of onCreate()====================================


    private void findId() {
        seekBar = findViewById(R.id.seekbar);
        seekBar2 = findViewById(R.id.seekbar2);
        tv_range = findViewById(R.id.tv_range);
        tv_distance = findViewById(R.id.tv_distance);
        tv_next = findViewById(R.id.tv_next);
        ll_advanced_search = findViewById(R.id.ll_advanced_search);
        iv_back = findViewById(R.id.iv_back);
        rangeSeekbar3 = findViewById(R.id.rangeSeekbar3);
        rangeSeekbar1 = findViewById(R.id.rangeSeekbar1);
        ysex = findViewById(R.id.ysex);
        ygender = findViewById(R.id.ygender);

        image_male = findViewById(R.id.image_male);
        image_fmale = findViewById(R.id.image_fmale);
        image_couple = findViewById(R.id.image_couple);
        image_male_to_male = findViewById(R.id.image_male_to_male);
        image_women_to_women = findViewById(R.id.image_women_to_women);
    }

    private void Onclick() {
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sexualo=ysex.getText().toString();
                String genderI=ygender.getText().toString();
                if(sexualo.equalsIgnoreCase("")){
                    ysex.setError("enter sexual orientation");
                    ysex.requestFocus();
                }else if(genderI.equalsIgnoreCase("")){
                    ygender.setError("enter gender identify");
                    ygender.requestFocus();
                }else {
                    requestProfileUpdate.setYsex(sexualo);
                    requestProfileUpdate.setYgender(genderI);

                    int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
                    String min_age = requestProfileUpdate.getFminsearchage();
                    String max_age = requestProfileUpdate.getFmaxsearchage();
                    String min_dis = requestProfileUpdate.getFmindisttance();
                    String max_dis = requestProfileUpdate.getFmaxdistance();
                    String sOrien  = requestProfileUpdate.getYsex();
                    String gender_i= requestProfileUpdate.getYgender();


                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> call = apiInterface.get_userSearch(u_id,min_age,max_age,min_dis,max_dis,sOrien,gender_i);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Toast.makeText(mContext, "Search updated", Toast.LENGTH_SHORT).show();
                            Log.v("search message:", response.message());
                            Intent intent = new Intent(mContext, WelcomeActivity.class);
                            intent.putExtra("model",requestProfileUpdate);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });


                }


            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FancyFeelingActivity.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
                finish();
            }
        });


        ll_advanced_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, AdavancedSearch.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
                finish();
            }
        });
    }
}
