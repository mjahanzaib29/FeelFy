package com.feelfy.feelfy.registration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class SpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);

        CheckPermissions();
    }

    void CheckPermissions() {
        RxPermissions.getInstance(SpashActivity.this)
                .request(
                       /* Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,*/
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA

                )
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        initialize(aBoolean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }

    public void initialize(boolean isAppInitialized) {
        if (isAppInitialized) {
            Thread background = new Thread() {
                public void run() {
                    try {

                        String login_status = AppPreferences.loadPreferences(SpashActivity.this, "LOGIN_STATUS");
                        if (login_status.equalsIgnoreCase("1")) {
                            Intent i = new Intent(SpashActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            sleep(1500);//3000 (3 Sec)
                            Intent i = new Intent(SpashActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            background.start();

        } else {
            //If one Of above permission not grant show alert (force to grant permission)
            AlertDialog.Builder builder = new AlertDialog.Builder(SpashActivity.this);
            builder.setTitle("Alert");
            builder.setMessage("All permissions necessary");

            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CheckPermissions();
                }
            });

            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            builder.show();
        }
    }
}
