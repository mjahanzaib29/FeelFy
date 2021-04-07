package com.feelfy.feelfy.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv_title, tv_next;
    Context mContext;
    RequestProfileUpdate requestProfileUpdate;
     String email;
     String id;
     String you;
     String name;
     String Dob;
     String Sexual;
     String Gender_identify;
     String FeelingString;
     String Fminsearchage;
     String height;
     String Fmaxsearchage;
     String Fmindisttance;
     String Fmaxdistance;
     String FsexString;
     String gender;
     String Fheight;
     String Fweight;
     String Fecolor;
     String Fhcolor;
     String Dfsmoke;
     String Ddalcohal;
     String Fhchildren;
     String Yage;
     String Ysex;
     String Ygender;
     String Yheight;
     String Yweight;
     String Yecolor;
     String Yhcolor;
     String Ydsmoke;
     String Ydalcohal;
     String Yhchildren;
    JsonObject jsonObject;
    ApiInterface apiInterface;


    String prevStarted = "prevStarted";
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = WelcomeActivity.this;
       // requestProfileUpdate=LoginActivity.requestProfileUpdate;

        Intent intent=getIntent();
        if(intent!=null) {
            requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");

        }

        email=requestProfileUpdate.getEmail();
        id=requestProfileUpdate.getId();
        you=requestProfileUpdate.getYou();
        name=requestProfileUpdate.getName();
        Dob=requestProfileUpdate.getDob();
        Sexual=requestProfileUpdate.getSexual();
        Gender_identify=requestProfileUpdate.getGender_identify();
        FeelingString=requestProfileUpdate.getFeeling();
        Fminsearchage=requestProfileUpdate.getFminsearchage();
        Fmaxsearchage=requestProfileUpdate.getFmaxsearchage();
        height=requestProfileUpdate.getHeight();
        Fmindisttance=requestProfileUpdate.getFmindisttance();
        Fmaxdistance=requestProfileUpdate.getFmaxdistance();
        FsexString=requestProfileUpdate.getFsexString();
        gender=requestProfileUpdate.getGender();
        Fheight=requestProfileUpdate.getFheight();
        Fweight=requestProfileUpdate.getFweight();
        Fecolor=requestProfileUpdate.getFecolor();
        Fhcolor=requestProfileUpdate.getFhcolor();
        Ddalcohal=requestProfileUpdate.getDdalcohal();
        Fhchildren=requestProfileUpdate.getFhchildren();
        Dfsmoke=requestProfileUpdate.getDfsmoke();
        Yage=requestProfileUpdate.getYage();
        Ysex=requestProfileUpdate.getYsex();
        Yecolor=requestProfileUpdate.getYecolor();
        Yhcolor=requestProfileUpdate.getYhcolor();
        Ydalcohal=requestProfileUpdate.getYdalcohal();
        Yhchildren=requestProfileUpdate.getYhchildren();
        Ydsmoke=requestProfileUpdate.getYdsmoke();
        Ygender=requestProfileUpdate.getYgender();
        Yheight=requestProfileUpdate.getYheight();
        Yweight=requestProfileUpdate.getYweight();


        tv_title = findViewById(R.id.tv_title);
        tv_next = findViewById(R.id.tv_next);

        String s = "Welcome to the 21st century "+name+ "!";

        SpannableString ss = new SpannableString(s);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getColor(R.color.color_login_background));
//        ss.setSpan(foregroundColorSpan, 28, 33, Spanned.SPAN_MARK_POINT);
        tv_title.setText(ss);

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateProfile();

            }
        });
    }

    private void updateProfile() {

        jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("You", you);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("Dob", Dob);
        jsonObject.addProperty("Sexual", Sexual);
        jsonObject.addProperty("Gender_identify", Gender_identify);
        jsonObject.addProperty("Feeling", FeelingString);
        jsonObject.addProperty("Fminsearchage", Fminsearchage);
        jsonObject.addProperty("height", height);
        jsonObject.addProperty("Fmaxsearchage", Fmaxsearchage);
        jsonObject.addProperty("Fmindisttance", Fmindisttance);
        jsonObject.addProperty("Fmaxdistance", Fmaxdistance);
        jsonObject.addProperty("FsexString", FsexString);
        jsonObject.addProperty("gender", gender);
        jsonObject.addProperty("Fheight", Fheight);
        jsonObject.addProperty("Fweight", Fweight);
        jsonObject.addProperty("Fecolor", Fecolor);
        jsonObject.addProperty("Fhcolor", Fhcolor);
        jsonObject.addProperty("Dfsmoke", Dfsmoke);
        jsonObject.addProperty("Ddalcohal", Ddalcohal);
        jsonObject.addProperty("Fhchildren", Fhchildren);
        jsonObject.addProperty("Yage", Yage);
        jsonObject.addProperty("Ysex", Ysex);
        jsonObject.addProperty("Ygender", Ygender);
        jsonObject.addProperty("Yheight", Yheight);
        jsonObject.addProperty("Yweight", Yweight);
        jsonObject.addProperty("Yecolor", Yecolor);
        jsonObject.addProperty("Yhcolor", Yhcolor);
        jsonObject.addProperty("Ydsmoke", Ydsmoke);
        jsonObject.addProperty("Ydalcohal", Ydalcohal);
        jsonObject.addProperty("Yhchildren", Yhchildren);

        AppPreferences.saveSharedPreferencesLogList(mContext,requestProfileUpdate);
        /*final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show();*/
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        //Call<LoginModel> call=apiInterface.login(amazonId);

        Call<JsonObject> call = apiInterface.updateProfile(jsonObject);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("Login_Response", response + "");
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                //progressDialog.dismiss();
                Toast.makeText(WelcomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
