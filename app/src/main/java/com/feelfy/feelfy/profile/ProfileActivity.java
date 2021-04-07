package com.feelfy.feelfy.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.feelfy.feelfy.fragment.HomeFragment;
import com.feelfy.feelfy.fragment.SubscribeFragment;
import com.feelfy.feelfy.fragment.MessageFragment;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.fragment.AccountSettingFragment;
import com.feelfy.feelfy.fragment.ChatFragment;
import com.feelfy.feelfy.fragment.ProfileFragment;
import com.kyleduo.switchbutton.SwitchButton;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView  image_chat,image_fastfeeling;
    RelativeLayout rel_menu, rel_settings;
    private Context mContext;
    SwitchButton sb_text_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = ProfileActivity.this;

        replaceFragment(new ProfileFragment());

        rel_menu = findViewById(R.id.rel_menu);
        rel_settings = findViewById(R.id.rel_settings);
        image_chat = findViewById(R.id.image_chat);
        sb_text_state = findViewById(R.id.sb_text_state);
        image_fastfeeling = findViewById(R.id.image_fastfeeling);

        rel_menu.setOnClickListener(this);
        rel_settings.setOnClickListener(this);
        image_chat.setOnClickListener(this);

    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, destFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_menu:
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_settings:
                replaceFragment(new AccountSettingFragment());
                sb_text_state.setVisibility(View.GONE);
                image_fastfeeling.setVisibility(View.VISIBLE);
                break;
            case R.id.image_chat:
                replaceFragment(new ChatFragment());
                sb_text_state.setVisibility(View.VISIBLE);
                image_fastfeeling.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            String fragmentName = fragment.getClass().getSimpleName();
            if (fragmentName.equals(HomeFragment.class.getSimpleName())) {
                closeApp();
            } else if (fragmentName.equals(AccountSettingFragment.class.getSimpleName())
                    || fragmentName.equals(MessageFragment.class.getSimpleName())
                    || fragmentName.equals(ChatFragment.class.getSimpleName())
                    || fragmentName.equals(SubscribeFragment.class.getSimpleName())
                    || fragmentName.equals(ProfileFragment.class.getSimpleName())
                    || fragmentName.equals(SubscribeFragment.class.getSimpleName())) {
                replaceFragment(new HomeFragment());
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public void closeApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure want to close this application");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                ProfileActivity.this.finishAffinity();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlack));
        alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlack));
    }
}
