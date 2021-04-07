package com.feelfy.feelfy.utils;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.fragment.HomeFragment;
import com.feelfy.feelfy.modules.CardInfo;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.registration.Extragallery;
import com.google.gson.JsonElement;
import com.kyleduo.switchbutton.SwitchButton;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

@Layout(R.layout.tinder_card_view)
public class TinderCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @View(R.id.locationNameTxt)
    private TextView locationNameTxt;

    @View(R.id.uid)
    private TextView uid;

    @View(R.id.tv_feeling)
    private TextView tv_feeling;

    @View(R.id.cardframelayout)
    private FrameLayout cardframelayout;


    private CardInfo mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, CardInfo profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    private String getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return "";

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return String.valueOf(age);
    }

    @Resolve
    private void onResolved() {
        if(mProfile.getImage() != null) {
            String image = "http://dotnetiks.com/feelfy/webServices/" + mProfile.getImage();
            Glide.with(mContext).load(image).into(profileImageView);
            nameAgeTxt.setText(mProfile.getNickname());
            locationNameTxt.setText("Age:" + getAge(mProfile.getDob()));


        }else{
            RequestOptions options = new RequestOptions()
                        .fitCenter()
                        .dontAnimate()
                        .placeholder(R.drawable.ic_attach_button)
                        .error(R.drawable.ic_attach_button)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH);
            Glide.with(mContext).load(mContext.getDrawable(R.drawable.profile))
                        .apply(options)
                        .into(profileImageView);
        }
    }

    //left dislike
    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(log_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String kol = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(kol);
                    JSONArray jsonArray = jsonObject.getJSONArray("sub");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        if (status.equals("norecord")){

                        }
                        else {
                            LeftSwipe();
                        }

                    }
                }
                catch (Exception e) {}
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {}
        });

    }

    // onclick card
    @SwipeCancelState
    private void onSwipeCancelState() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        final String btn = preferences.getString("btn", "");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(log_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String kol = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(kol);
                    JSONArray jsonArray = jsonObject.getJSONArray("sub");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        if (status.equals("1") && btn.equals("1"))
                        {
                            ////
                            Log.d("EVENT", "onSwipeCancelState");
                            // Gallery();
                            int mCard_id = Integer.parseInt(mProfile.getUser_id());
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Name",String.valueOf(mCard_id));
                            editor.apply();
                            Intent intent = new Intent(mContext, Extragallery.class);
                            mContext.startActivity(intent);
                            ///
                        }
                        else{
//                            Toast.makeText(mContext, "btn close", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    //right like
    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(log_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String kol = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(kol);
                    JSONArray jsonArray = jsonObject.getJSONArray("sub");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        if (status.equals("norecord")){
                            RightSwipe();
                        }
                        else {
                            PremiumRightSwipe();
                        }

                    }
                }
                catch (Exception e) {}
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {}
        });
    }

    //cardswiperight
    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(log_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String kol = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(kol);
                    JSONArray jsonArray = jsonObject.getJSONArray("sub");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        if (status.equals("norecord")){
                            RightSwipe();
                        }
                        else {
                            PremiumRightSwipe();
                        }

                    }
                }
                catch (Exception e) {}
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {}
        });

    }

    //cardswipeleft
    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(log_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String kol = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(kol);
                    JSONArray jsonArray = jsonObject.getJSONArray("sub");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String status = jsonObject1.getString("status");
                        if (status.equals("norecord")){

                        }
                        else {
                            LeftSwipe();
                        }

                    }
                }
                catch (Exception e) {}
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {}
        });

    }

    public void PremiumRightSwipe(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        int mCard_id = Integer.parseInt(mProfile.getUser_id());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.premium_rightswipe(log_id,mCard_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(mContext, "Is Like", Toast.LENGTH_SHORT).show();
                //card_id = 0;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void RightSwipe(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        int mCard_id = Integer.parseInt(mProfile.getUser_id());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.get_rightswiperesponse(log_id,mCard_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(mContext, "Is Like", Toast.LENGTH_SHORT).show();
                //card_id = 0;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    public void LeftSwipe(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        int card_id = Integer.parseInt(mProfile.getUser_id());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.get_leftswiperesponse(log_id,card_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(mContext, "DisLike", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
