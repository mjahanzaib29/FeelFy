package com.feelfy.feelfy.subscribe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.profile.ProfileActivity;

public class SubscribeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image_gold, image_ventearriba, image_fastfeeling, image_bronze, image_silver,iv_back;
    Context mContext;
    String subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        mContext = SubscribeActivity.this;

        image_gold = findViewById(R.id.image_gold);
        image_ventearriba = findViewById(R.id.image_ventearriba);
        image_fastfeeling = findViewById(R.id.image_fastfeeling);
        image_bronze = findViewById(R.id.image_bronze);
        image_silver = findViewById(R.id.image_silver);
        iv_back = findViewById(R.id.iv_back);

        image_gold.setOnClickListener(this);
        image_ventearriba.setOnClickListener(this);
        image_fastfeeling.setOnClickListener(this);
        image_bronze.setOnClickListener(this);
        image_silver.setOnClickListener(this);
        iv_back.setOnClickListener(this);
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
            case R.id.iv_back:
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
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
}
