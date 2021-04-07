package com.feelfy.feelfy.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.fragment.AccountSettingFragment;
import com.feelfy.feelfy.fragment.ChatFragment;
import com.feelfy.feelfy.fragment.HomeFragment;
import com.feelfy.feelfy.fragment.SubscribeFragment;
import com.feelfy.feelfy.fragment.MessageFragment;
import com.feelfy.feelfy.fragment.ProfileFragment;
import com.feelfy.feelfy.profile.ProfileActivity;
import com.kyleduo.switchbutton.SwitchButton;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView image_chat;
    RelativeLayout rel_menu, rel_profile;
    private Context mContext;
    SwitchButton sb_text_state;
    public String btn_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        replaceFragment(new HomeFragment());

        rel_menu = findViewById(R.id.rel_menu);
        rel_profile = findViewById(R.id.rel_profile);
        image_chat = findViewById(R.id.image_chat);
        sb_text_state = findViewById(R.id.sb_text_state1);



        rel_menu.setOnClickListener(this);
        rel_profile.setOnClickListener(this);
        image_chat.setOnClickListener(this);
    }

    public void dowork(){
        sb_text_state.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frameLayout,new HomeFragment());
                //fragmentTransaction.commit();
                if(isChecked == true){

                    btn_status = "true";
                    ft.detach(new HomeFragment());
                    ft.attach(new HomeFragment());
                    ft.commit();
//                    Toast.makeText(mContext, "true", Toast.LENGTH_SHORT).show();
                }else {
                    btn_status = "false";
                    ft.detach(new HomeFragment());
                    ft.attach(new HomeFragment());
                    ft.commit();

//                    Toast.makeText(mContext, "false", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void replaceFragment(Fragment destFragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, destFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_menu:
                replaceFragment(new HomeFragment());
                break;
            case R.id.rel_profile:
                Intent intent = new Intent(mContext, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.image_chat:
                replaceFragment(new ChatFragment());
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
                HomeActivity.this.finishAffinity();
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

