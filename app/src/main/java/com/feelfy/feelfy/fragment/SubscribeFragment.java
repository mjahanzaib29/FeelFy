package com.feelfy.feelfy.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.profile.ProfileActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.feelfy.feelfy.subscribe.SubscribeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscribeFragment extends Fragment implements View.OnClickListener {


    private ImageView image_gold, image_ventearriba, image_fastfeeling, image_bronze, image_silver;
    Context mContext;
    String subscribe;
    int user_id;
    RequestProfileUpdate requestProfileUpdate;
    ApiInterface apiInterface;


    public SubscribeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribe, container, false);

        mContext = getActivity();

        requestProfileUpdate=AppPreferences.loadSharedPreferencesLogList(mContext);
        image_gold = view.findViewById(R.id.image_gold);
        image_ventearriba = view.findViewById(R.id.image_ventearriba);
        image_fastfeeling = view.findViewById(R.id.image_fastfeeling);
        image_bronze = view.findViewById(R.id.image_bronze);
        image_silver = view.findViewById(R.id.image_silver);

        image_gold.setOnClickListener(this);
        image_ventearriba.setOnClickListener(this);
        image_fastfeeling.setOnClickListener(this);
        image_bronze.setOnClickListener(this);
        image_silver.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_gold:
                subscribe = "gold";
                showdialog(subscribe);
                break;
            case R.id.image_silver:
                subscribe = "silver";
                showdialog(subscribe);
                break;
            case R.id.image_bronze:
                subscribe = "bronze";
                showdialog(subscribe);
                break;
            case R.id.image_fastfeeling:
                subscribe = "fastfeeling";
                showdialog(subscribe);
                break;
            case R.id.image_ventearriba:
                subscribe = "ventearriba";
                showdialog(subscribe);
                break;
        }
    }

    private void showdialog(String subscribe) {
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View deleteDialogView = factory.inflate(R.layout.subsribe_dialog_view, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(mContext).create();
        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        deleteDialog.setView(deleteDialogView);
        switch (subscribe) {
            case "gold":
                deleteDialogView.findViewById(R.id.rel_gold).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.image_gold).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.tv_gold).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int gold_point = 4;
                                purchase("gold",gold_point);
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                startActivity(intent);
                                AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "true");
                                deleteDialog.dismiss();
                            }
                        });
                break;
            case "silver":
                deleteDialogView.findViewById(R.id.rel_silver).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.image_silver).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.tv_silver).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int silver_point = 2;
                                purchase("silver",silver_point);
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                startActivity(intent);
                                AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "true");
                                deleteDialog.dismiss();
                            }
                        });
                break;
            case "bronze":
                deleteDialogView.findViewById(R.id.rel_bronze).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.image_bronze).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.tv_bronze).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int bronze_point = 1;
                                purchase("bronze",bronze_point);
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                startActivity(intent);
                                AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "true");
                                deleteDialog.dismiss();
                            }
                        });
                break;
            case "fastfeeling":
                deleteDialogView.findViewById(R.id.rel_fastfeeling).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.rel_fastfeeling_image).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.tv_fastfeeling).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int feeling_point = 1;
                                purchaseIndividual("fastfeeling",feeling_point);
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                startActivity(intent);
                                AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "true");
                                deleteDialog.dismiss();
                            }
                        });
                break;
            case "ventearriba":
                deleteDialogView.findViewById(R.id.rel_ventearriba).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.rel_vente).setVisibility(View.VISIBLE);
                deleteDialogView.findViewById(R.id.tv_ventearriba).
                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int comeup_point = 1;
                                purchaseIndividual("ventearriba",comeup_point);
                                Intent intent = new Intent(mContext, HomeActivity.class);
                                startActivity(intent);
                                AppPreferences.savePreferences(mContext, "SUBSCRIPTION", "true");
                                deleteDialog.dismiss();
                            }
                        });
                break;

        }
        deleteDialogView.findViewById(R.id.closeRL).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
        deleteDialog.show();
    }


    public void purchase(final String purchase_type, int point){
        user_id = Integer.parseInt(requestProfileUpdate.getUser_id());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar currentTime = Calendar.getInstance();
        String strDate = sdfDate.format(currentTime.getTime());
        currentTime.add(Calendar.MONTH,1);
        String endDate = sdfDate.format(currentTime.getTime());


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.pkgpurchase(user_id,strDate,endDate,purchase_type,point);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.message().equals("OK")){
                   // showdialog(purchase_type);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Error in Purchase", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void purchaseIndividual(final String purchase_type, int point){
        user_id = Integer.parseInt(requestProfileUpdate.getUser_id());

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar currentTime = Calendar.getInstance();
        String strDate = sdfDate.format(currentTime.getTime());
        currentTime.add(Calendar.DAY_OF_MONTH,1);
        String endDate = sdfDate.format(currentTime.getTime());


        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.pkgpurchase(user_id,strDate,endDate,purchase_type,point);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.message().equals("OK")){
                   // showdialog(purchase_type);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Error in Purchase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
