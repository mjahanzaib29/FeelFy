package com.feelfy.feelfy.description;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.fragment.ProfileFragment;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.profile.ProfileActivity;
import com.feelfy.feelfy.registration.InformationActivity;
import com.feelfy.feelfy.registration.LoginActivity;
import com.feelfy.feelfy.registration.WelcomeActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvancedDiscriptionActivity extends AppCompatActivity {


    SeekBar seekBar, seekBar2;
    TextView tv_cm, tv_we, tv_next;
    ImageView iv_back;
    Context mContext;
    ApiInterface apiInterface;
    RequestProfileUpdate requestProfileUpdate;

    Spinner sp_eye_color, sp_hair_color, sp_smoke, sp_alcohol, sp_children;
    String[] eye_color = {"Eye color", "Black", "Brown", "Blue", "Green"};
    String[] Hair_color = {"Hair color", "Black", "Brown", "White"};
    String[] smoke = {"Do you smoke?", "Yes", "No"};
    String[] drink = {"Do you drink alcohol?", "Yes", "No"};
    String[] Have_children = {"Have children?", "Yes", "No"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_discription);

        mContext = AdvancedDiscriptionActivity.this;
        //requestProfileUpdate= LoginActivity.requestProfileUpdate;
        requestProfileUpdate= AppPreferences.loadSharedPreferencesLogList(mContext);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String reg_id = preferences.getString("reg", "");

        if(reg_id == ""){
            get_preference_one(Integer.parseInt(requestProfileUpdate.getUser_id()));
        }else {
            get_preference_one(Integer.parseInt(reg_id));
        }
//        String id = forid();
//        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();


//        Intent intent=getIntent();
//        if(intent!=null) {
//            try {
//                requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        seekBar = findViewById(R.id.seekbar);
        seekBar2 = findViewById(R.id.seekbar2);
        tv_cm = findViewById(R.id.tv_cm);
        tv_we = findViewById(R.id.tv_we);
        tv_next = findViewById(R.id.tv_next);
        iv_back = findViewById(R.id.iv_back);

        sp_eye_color = findViewById(R.id.sp_eye_color);
        sp_hair_color = findViewById(R.id.sp_hair_color);
        sp_smoke = findViewById(R.id.sp_smoke);
        sp_alcohol = findViewById(R.id.sp_alcohol);
        sp_children = findViewById(R.id.sp_children);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String range = String.valueOf(i);
                tv_cm.setText(range);
                requestProfileUpdate.setFheight(range);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String distance = String.valueOf(i);
                tv_we.setText(distance);
                requestProfileUpdate.setFweight(distance);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String u_height = requestProfileUpdate.getFheight();
                final String u_weight = requestProfileUpdate.getFweight();
                final String u_ecolor = requestProfileUpdate.getFecolor();
                final String u_hcolor = requestProfileUpdate.getFhcolor();
                final String u_smoke  = requestProfileUpdate.getDfsmoke();
                final String u_alcohal = requestProfileUpdate.getDdalcohal();
                final String u_children = requestProfileUpdate.getFhchildren();
                final int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
//                final String u_email = requestProfileUpdate.getEmail();

                if(u_height == null){
                    seekBar.setFocusable(true);
                    seekBar.requestFocus();
                }
//                else if(u_weight == null){
//                    Toast.makeText(mContext, "Enter weight", Toast.LENGTH_SHORT).show();
//                    seekBar2.requestFocus();
//                }else if(u_ecolor == null){
//                    Toast.makeText(mContext, "Enter eye color", Toast.LENGTH_SHORT).show();
//                    sp_eye_color.setFocusable(true);
//                    sp_eye_color.requestFocus();
//                }else if(u_hcolor == null){
//                    Toast.makeText(mContext, "Enter hair color", Toast.LENGTH_SHORT).show();
//                    sp_hair_color.requestFocus();
//                }else if(u_smoke == null){
//                    Toast.makeText(mContext, "Enter smoke detail", Toast.LENGTH_SHORT).show();
//                    sp_smoke.requestFocus();
//                }else if(u_alcohal == null){
//                    Toast.makeText(mContext, "Enter alcohol detail", Toast.LENGTH_SHORT).show();
//                    sp_alcohol.requestFocus();
//                }else if(u_children == null){
//                    Toast.makeText(mContext, "Enter children detail", Toast.LENGTH_SHORT).show();
//                    sp_children.requestFocus();
//                }else {


                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseBody> profileUpdateCall = apiInterface.get_preference_one(u_id, u_height, u_weight, u_ecolor, u_hcolor, u_smoke, u_alcohal, u_children);
                    profileUpdateCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.message().equals("OK")) {
                                Toast.makeText(mContext,  "Profile Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, InformationActivity.class);
                                intent.putExtra("model", requestProfileUpdate);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(mContext, "Not updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mContext, "Failed to update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
            }
        });

        sp_eye_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eye_value=eye_color[position];
                if(eye_value!=null){
                    try {
                        requestProfileUpdate.setFecolor(eye_value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_hair_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hair_value=Hair_color[position];
                try {
                    requestProfileUpdate.setFhcolor(hair_value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_smoke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String smoke_value=smoke[position];
                try {
                    requestProfileUpdate.setDfsmoke(smoke_value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_alcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String alcohal_value=drink[position];
                try {
                    requestProfileUpdate.setDdalcohal(alcohal_value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String children_value=Have_children[position];
                try {
                    requestProfileUpdate.setFhchildren(children_value);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter sp_eye_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eye_color);
        sp_eye_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_eye_color.setAdapter(sp_eye_adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_eye_color);
            assert popupWindow != null;
            popupWindow.setHeight(550);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        ArrayAdapter sp_hair_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Hair_color);
        sp_eye_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hair_color.setAdapter(sp_hair_adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_hair_color);
            assert popupWindow != null;
            popupWindow.setHeight(550);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        ArrayAdapter sp_smoke_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smoke);
        sp_eye_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_smoke.setAdapter(sp_smoke_adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_smoke);
            assert popupWindow != null;
            popupWindow.setHeight(550);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayAdapter sp_alcohol_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, drink);
        sp_alcohol_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_alcohol.setAdapter(sp_alcohol_adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_alcohol);
            assert popupWindow != null;
            popupWindow.setHeight(550);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        ArrayAdapter sp_children_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Have_children);
        sp_children_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_children.setAdapter(sp_children_adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_children);
            assert popupWindow != null;
            popupWindow.setHeight(550);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public String forid(){
//
//        if (reg_id != null){
//            Toast.makeText(mContext,reg_id, Toast.LENGTH_SHORT).show();
//            return reg_id;
//        }
//        else {
//            reg_id = requestProfileUpdate.getUser_id();
//            Toast.makeText(mContext, reg_id, Toast.LENGTH_SHORT).show();
//            return reg_id;
//        }
//    }



    public void get_preference_one(int user_id){
        apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<JsonElement> call = apiInterface.get_user_preference_one(user_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String resone=response.body().toString();
                try {
                    JSONObject jsonObject = new JSONObject(resone);
                    JSONArray jsonArray = jsonObject.getJSONArray("one");
                    for (int i =0 ; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if(jsonObject1.getString("weight").equals("null")){
                            tv_we.setText("10");
                        }else {
                            tv_we.setText(jsonObject1.getString("weight"));
                            seekBar2.setProgress(Integer.parseInt(tv_we.getText().toString()));
                        }
                        if(jsonObject1.getString("height").equals("null")){
                            tv_cm.setText("10");
                        }else {
                            tv_cm.setText(jsonObject1.getString("height"));
                            seekBar.setProgress(Integer.parseInt(tv_cm.getText().toString()));
                            sp_eye_color.setSelection(((ArrayAdapter<String>)sp_eye_color.getAdapter()).getPosition(jsonObject1.getString("eyeColor")));
                            sp_hair_color.setSelection(((ArrayAdapter<String>)sp_hair_color.getAdapter()).getPosition(jsonObject1.getString("hairColor")));
                            sp_smoke.setSelection(((ArrayAdapter<String>)sp_smoke.getAdapter()).getPosition(jsonObject1.getString("smoke")));
                            sp_alcohol.setSelection(((ArrayAdapter<String>)sp_alcohol.getAdapter()).getPosition(jsonObject1.getString("drink")));
                            sp_children.setSelection(((ArrayAdapter<String>)sp_children.getAdapter()).getPosition(jsonObject1.getString("haveChildren")));

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
