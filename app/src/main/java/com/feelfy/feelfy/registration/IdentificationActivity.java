package com.feelfy.feelfy.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;

public class IdentificationActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView image_male, image_female, image_couple, image_men_to_men, image_women_to_women, image_back;
    Context mContext;
    TextView tv_next;
    Intent intent;
    String title;
    String name,id;
    RequestProfileUpdate requestProfileUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        mContext = IdentificationActivity.this;
        //requestProfileUpdate=LoginActivity.requestProfileUpdate;

        Intent intent=getIntent();
        if(intent!=null) {
        requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
        }
        /*if(requestProfileUpdate.getEmail()!=null){
            name=requestProfileUpdate.getEmail();
            Log.d("name",name);
            Toast.makeText(IdentificationActivity.this,name,Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(IdentificationActivity.this,name,Toast.LENGTH_SHORT).show();*/

     /*   name=requestProfileUpdate.getName();
        id=requestProfileUpdate.getId();
        Log.d("name",name);
        Log.d("id",id);*/
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
                requestProfileUpdate.setYou("Male");
                break;
            case R.id.image_female:
                AppPreferences.savePreferences(mContext, "TITLE", "Female");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setYou("Female");

                break;
            case R.id.image_couple:
                AppPreferences.savePreferences(mContext, "TITLE", "Couple");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setYou("Couple");
                break;
            case R.id.image_men_to_men:
                AppPreferences.savePreferences(mContext, "TITLE", "Male_to_Male");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setYou("Male to Male");
                break;
            case R.id.image_women_to_women:
                AppPreferences.savePreferences(mContext, "TITLE", "Female_to_Female");
                image_male.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_female.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_couple.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_men_to_men.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack), PorterDuff.Mode.SRC_IN);
                image_women_to_women.setColorFilter(ContextCompat.getColor(mContext, R.color.color_login_background), PorterDuff.Mode.SRC_IN);
                requestProfileUpdate.setYou("Female to Female");
                break;
            case R.id.tv_next:
                title = AppPreferences.loadPreferences(mContext, "TITLE");
                if (title.equalsIgnoreCase("")) {
                    Toast.makeText(mContext, "Please Select Status", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(mContext, InformationActivity.class);
                    intent.putExtra("model",requestProfileUpdate);
                    mContext.startActivity(intent);
                    finish();
                }
                break;
            case R.id.iv_back:
                intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
                finish();
                break;
        }
    }
}