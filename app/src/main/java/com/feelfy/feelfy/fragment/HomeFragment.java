package com.feelfy.feelfy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.CardInfo;
import com.feelfy.feelfy.modules.CardsResponse;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.profile.ProfileActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.feelfy.feelfy.utils.TinderCard;
import com.google.gson.JsonElement;
import com.kyleduo.switchbutton.SwitchButton;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    ApiInterface apiInterface;
    RequestProfileUpdate requestProfileUpdate;
    public String id,subscription;
    public String card_id , status;
    ImageButton acceptBtn,rejectBtn;
    SwitchButton sb_text_state;
    List<CardInfo> cardInfoList = new ArrayList<>();

    public String btn;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = getActivity();
        requestProfileUpdate=AppPreferences.loadSharedPreferencesLogList(mContext);
        id=requestProfileUpdate.getUser_id();
//        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
        acceptBtn = view.findViewById(R.id.acceptBtn);
        rejectBtn = view.findViewById(R.id.rejectBtn);
        sb_text_state = getActivity().findViewById(R.id.sb_text_state1);
        subscription = AppPreferences.loadPreferences(mContext, "SUBSCRIPTION");
        mSwipeView = view.findViewById(R.id.swipeView);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loggin",id);
        editor.putString("btn",btn);
        editor.apply();

        cards(Integer.parseInt(id));

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(10)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        Checksub();
        //SubprefCheck();
        return view;
    }

    // checking if user purchased pkg or not
    private void SubprefCheck(){
        if (!subscription.equalsIgnoreCase("true")) {
            LayoutInflater factory = LayoutInflater.from(mContext);
            final View deleteDialogView = factory.inflate(R.layout.dialog_view, null);
            final AlertDialog deleteDialog = new AlertDialog.Builder(mContext).create();
            deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            deleteDialog.setView(deleteDialogView);

            deleteDialogView.findViewById(R.id.buttonOk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof HomeActivity) {
                        ((HomeActivity) mContext).replaceFragment(new SubscribeFragment());
                    } else if (mContext instanceof ProfileActivity) {
                        ((ProfileActivity) mContext).replaceFragment(new SubscribeFragment());
                    }
                    deleteDialog.dismiss();
                }
            });
            deleteDialogView.findViewById(R.id.closeRL).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDialog.dismiss();
                }
            });
            deleteDialog.show();
        }
    }

    // checking if pkg status is 1 or not// pkg is valid or not
    private void Checksub(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.getSub(Integer.parseInt(id));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                String kol = response.body().toString();
                Log.v("kol",kol);

                    try {
                        JSONObject jsonObject = new JSONObject(kol);
                        JSONArray jsonArray = jsonObject.getJSONArray("sub");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String purchase_type = jsonObject1.getString("purchase_type");
                            status = jsonObject1.getString("status");
                            String  end_time = jsonObject1.getString("end_time");
                             if(status.equals("0")){
//                                 Toast.makeText(mContext, "statos 0", Toast.LENGTH_SHORT).show();
                             }else{
                                 pkg_validate(end_time);
                             }
                        }

                    }
                    catch (Exception e) {
//                        Toast.makeText(mContext, "kol not working", Toast.LENGTH_SHORT).show();
                        AppPreferences.savePreferences(mContext,"SUBSCRIPTION","false");
                        SubprefCheck();
                    }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }

    //checking validity of pkg with current time if current time passed expiry make pkg status 0
    public void pkg_validate(String end_time){

        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar currentTime = Calendar.getInstance();
            String strDate = sdfDate.format((currentTime.getTime()));
            Date startdate = sdfDate.parse(strDate);
            Date enddate_db = sdfDate.parse(end_time);
            if (enddate_db.before(startdate)) {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RegisterResponse> call1 = apiInterface.checkvalidity(Integer.parseInt(id));
                call1.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.body().getPkg().equals("package updated")) {
                            AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "false");
                            SubprefCheck();
                            Toast.makeText(mContext, "package expired", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(mContext, "package not response", Toast.LENGTH_SHORT).show();
                    }
                });
            }


            //switch button click work
            else {
                sb_text_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (sb_text_state.isChecked() == true){
                            pkgdetail();
//                            sb_text_state.setClickable(false);
                                btn= "1";

                        }else {
                                btn= "0";
                        }
                    }
                });

                Toast.makeText(mContext, "package valid to activate", Toast.LENGTH_SHORT).show();
            }


        } catch (ParseException e) {
            AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "false");
            SubprefCheck();
            Toast.makeText(mContext, "package expired", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // checking how many pkg instances are left in package_detail table
    String status_dtl;
    public void pkgdetail(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call1 = apiInterface.get_pkgdetail(Integer.parseInt(id));
        call1.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String detail = response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(detail);
                    JSONArray jsonArray = jsonObject.getJSONArray("pkgdetail");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        status_dtl = jsonObject1.getString("status");
                    }
                        if (status_dtl.equals("0")){
                            sb_text_state.setChecked(false);
                            apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<RegisterResponse> bundle = apiInterface.updatepkgstatus(Integer.parseInt(requestProfileUpdate.getUser_id()));
                            bundle.enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    String getPkgstatus = response.body().getPkgstatus();
                                    if (getPkgstatus.equals("Package Status Changed")){
                                        Toast.makeText(mContext, "Bundle Expired", Toast.LENGTH_SHORT).show();
                                        AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "false");
                                        SubprefCheck();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                                }
                            });

                        }
                        //bundle
                        else {
                            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar currentTimeDate = Calendar.getInstance();
                            String pkgstart = sdfDate.format(currentTimeDate.getTime());
                            currentTimeDate.add(Calendar.DAY_OF_MONTH,1);
                            String pkgexpire = sdfDate.format(currentTimeDate.getTime());

                            try{
                                Date pkgstarttime = sdfDate.parse(pkgstart);
                                Date  pkgexptime = sdfDate.parse(pkgexpire);

                                //current time is greater than expire time
                                if (pkgexptime.before(pkgstarttime)){
                                    sb_text_state.setClickable(true);
                                }
                                else {
                                    sb_text_state.setClickable(false);
                                }
                            }catch (Exception e){

                            }

                            Log.v("dates:",pkgstart + pkgexpire);
                            apiInterface = ApiClient.getClient().create(ApiInterface.class);
                            Call<RegisterResponse> bundle = apiInterface.using_package_switchbtn(Integer.parseInt(requestProfileUpdate.getUser_id()),pkgstart,pkgexpire);
                            bundle.enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    String bundledetails = response.body().getBundledetail();
                                    if (bundledetails == "Details updated"){
                                        Toast.makeText(mContext, "Package activated for 24Hrs", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                                }
                            });
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    //cards info
    private void cards(int id){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CardInfo>> call = apiInterface.get_card_data(id);
        call.enqueue(new Callback<List<CardInfo>>() {
            @Override
            public void onResponse(Call<List<CardInfo>> call, final Response<List<CardInfo>> response) {
                try {
                    cardInfoList= response.body();
                    for (CardInfo cardInfo1 :cardInfoList){
                        mSwipeView.addView(new TinderCard(mContext, cardInfo1, mSwipeView));
                    }

                    rejectBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSwipeView.doSwipe(false);
                        }
                    });
                    acceptBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSwipeView.doSwipe(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<CardInfo>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Check network", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
