package com.feelfy.feelfy.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.fragment.HomeFragment;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.modules.UserExtraPics;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Extragallery extends AppCompatActivity {
    ImageView img1,img2,img3,img4,img5,img6,closebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extrapicgallery_dialog);
        closebtn = (ImageView) findViewById(R.id.close_btn_gallery);
        img1 = (ImageView) findViewById(R.id.picture_dialog1);
        img2 = (ImageView) findViewById(R.id.picture_dialog2);
        img3 = (ImageView) findViewById(R.id.picture_dialog3);
        img4 = (ImageView) findViewById(R.id.picture_dialog4);
        img5 = (ImageView) findViewById(R.id.picture_dialog5);
        img6 = (ImageView) findViewById(R.id.picture_dialog6);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("Name", "");

//        if ( getFragmentManager().getBackStackEntryCount() > 0)
//        {
//            getFragmentManager().popBackStack();
//            return;
//        }
//        super.onBackPressed();

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    HomeFragment fragment = new HomeFragment();
//                    if(fragment != null)
//                    {
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frameLayout, fragment);
////                        transaction.addToBackStack(null);
//                        transaction.commit();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserExtraPics> call = apiInterface.get_extrapics(Integer.parseInt(name));
        call.enqueue(new Callback<UserExtraPics>() {
            @Override
            public void onResponse(Call<UserExtraPics> call, Response<UserExtraPics> response) {
                String image1 = response.body().getEimage1();
                String image2 = response.body().getEimage2();
                String image3 = response.body().getEimage3();
                String image4 = response.body().getEimage4();
                String image5 = response.body().getEimage5();
                String image6 = response.body().getEimage6();
                if (image1 !=null){
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image1).into(img1);
                }
                if (image2 !=null) {
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image2).into(img2);
                }
                if (image3 !=null) {
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image3).into(img3);
                }
                if (image4 !=null) {
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image4).into(img4);
                }
                if (image5 !=null) {
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image5).into(img5);
                }
                if (image6 !=null) {
                    Picasso.with(getApplicationContext()).load("http://dotnetiks.com/feelfy/webServices/" + image6).into(img6);
                }
            }
            @Override
            public void onFailure(Call<UserExtraPics> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No extra images", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
