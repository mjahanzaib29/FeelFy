package com.feelfy.feelfy.description;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.registration.LoginActivity;
import com.feelfy.feelfy.registration.RefineActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;

import java.lang.reflect.Field;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdavancedSearch extends AppCompatActivity {

    SeekBar seekBar, seekBar2;
    TextView tv_cm, tv_we, tv_apply;
    ImageView iv_back;
    Context mContext;
    ApiInterface apiInterface;


    //String app_name = getResources().getString(R.string.please_wait);
    Spinner sp_eye_color, sp_hair_color, sp_smoke, sp_alcohol, sp_children;
    String[] eye_color,Hair_color,smoke,drink,Have_children;

    //    String[] eye_color = {"Eye Color", "Black", "Brown", "Blue", "Green"};
//    String[] Hair_color = {"Hair color",  "Black", "Brown", "White"};
//    String[] smoke = {"Do you smoke?", "Yes", "No"};
//    String[] drink = {"Do you drink alcohol?", "Yes", "No"};
//    String[] Have_children = {"Have children?", "Yes", "No"};
    RequestProfileUpdate requestProfileUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adavanced_search);

        String eyecolor = getString(R.string.eye_color);
        String haircolor = getString(R.string.hair_color);
        String dsmoke = getString(R.string.smoke);
        String ddrink = getString(R.string.drink);
        String have_child = getString(R.string.have_child);
        String dyes = getString(R.string.yes);
        String no = getString(R.string.no);
        String black = getString(R.string.black);
        String brown = getString(R.string.brown);
        String blue = getString(R.string.blue);
        String green = getString(R.string.green);
        String white = getString(R.string.white);

//        Toast.makeText(getApplicationContext(), eyecolor, Toast.LENGTH_LONG).show();
        eye_color = new String[] {eyecolor, black, brown, blue, green};
        Hair_color =  new String[] {haircolor , black , brown , white};
        smoke = new String[] {dsmoke , dyes, no};
        drink = new String[] {ddrink , dyes , no};
        Have_children = new String[] {have_child , dyes , no};
        mContext = AdavancedSearch.this;
       // requestProfileUpdate= LoginActivity.requestProfileUpdate;
        requestProfileUpdate= AppPreferences.loadSharedPreferencesLogList(mContext);

        Intent intent=getIntent();
        if(intent!=null) {
            requestProfileUpdate = (RequestProfileUpdate) intent.getSerializableExtra("model");
        }

        seekBar = findViewById(R.id.seekbar);
        seekBar2 = findViewById(R.id.seekbar2);
        tv_cm = findViewById(R.id.tv_cm);
        tv_we = findViewById(R.id.tv_we);
        tv_apply = findViewById(R.id.tv_apply);
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
                requestProfileUpdate.setYheight(range);


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
                requestProfileUpdate.setYweight(distance);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int u_id = Integer.parseInt(requestProfileUpdate.getUser_id());
                String height = requestProfileUpdate.getYheight();
                String weight = requestProfileUpdate.getWeight();
                String eyecolor = requestProfileUpdate.getYecolor();
                String haircolor = requestProfileUpdate.getYhcolor();
                String smoke = requestProfileUpdate.getYdsmoke();
                String drink = requestProfileUpdate.getYdalcohal();
                String children = requestProfileUpdate.getYhchildren();

                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.get_userAdvancesearch(u_id,height,weight,eyecolor,haircolor,smoke,drink,children);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(mContext, "Advanced Search updated", Toast.LENGTH_SHORT).show();
                        Log.v("advancesearch:", response.message());
                        Intent intent = new Intent(mContext, RefineActivity.class);
                        intent.putExtra("model",requestProfileUpdate);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "Search not updated", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RefineActivity.class);
                intent.putExtra("model",requestProfileUpdate);
                startActivity(intent);
                finish();
            }
        });


        sp_eye_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eye_value=eye_color[position];
                requestProfileUpdate.setYecolor(eye_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_hair_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hair_value=Hair_color[position];
                requestProfileUpdate.setYhcolor(hair_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_smoke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String smoke_value=smoke[position];
                requestProfileUpdate.setYdsmoke(smoke_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_alcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String alcohal_value=drink[position];
                requestProfileUpdate.setYdalcohal(alcohal_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_children.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String children_value=Have_children[position];
                requestProfileUpdate.setYhchildren(children_value);
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
}
