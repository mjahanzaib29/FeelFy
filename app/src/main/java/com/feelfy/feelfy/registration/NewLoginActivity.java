package com.feelfy.feelfy.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.description.AdvancedDiscriptionActivity;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLoginActivity extends AppCompatActivity {

    EditText et_email,et_passsword,et_r_password;
    TextView tv_login;
    String email,password,repeatpassword;
    ApiInterface apiInterface;
    RequestProfileUpdate requestProfileUpdate=new RequestProfileUpdate();
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        find_ids();

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 email = et_email.getText().toString();
                 password = et_passsword.getText().toString();
                 repeatpassword = et_r_password.getText().toString();

                if (email.equalsIgnoreCase("")) {
                    et_email.setError("Enter Email OR Phone No.");
                    et_email.requestFocus();
                } else if (!checkValidation(email)) {
                    et_email.setError("Enter Valid Email or Phone No.");
                    et_email.requestFocus();
                } else if (password.equalsIgnoreCase("")) {
                    et_passsword.setError("Enter Password");
                    et_passsword.requestFocus();
                } /*else if (!validate(password)) {
                    et_passsword.setError("Not a valid password!");
                    et_passsword.requestFocus();
                }*/else if (!repeatpassword.equals(password)) {
                    et_passsword.setError("Password does not matches!");
                    et_passsword.requestFocus();
                } else {
//                    doLogin(email, password);
                    doRegister(email,password);
                }
            }
        });
    }

    private void doRegister(String email, String password){
        final ProgressDialog progressDialog = new ProgressDialog(NewLoginActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegisterResponse> call = apiInterface.registration(email,password);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressDialog.dismiss();
                String res = response.body().getResponse();
                try {
                    requestProfileUpdate.setUser_id(res);
                    AppPreferences.saveSharedPreferencesLogList(getApplicationContext(), requestProfileUpdate);
                    AppPreferences.savePreferences(NewLoginActivity.this, "LOGIN_STATUS", "1");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("reg",res);
                    editor.apply();
                } catch (NumberFormatException  e) {
                    System.out.println("not a number");
                }
                AppPreferences.saveSharedPreferencesLogList(getBaseContext(), requestProfileUpdate);
                Toast.makeText(NewLoginActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(NewLoginActivity.this, AdvancedDiscriptionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(NewLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }




//    private void doLogin(String email, String password) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(NewLoginActivity.this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
//        progressDialog.show();
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        jsonObject = new JsonObject();
//        jsonObject.addProperty("email", email);
//        jsonObject.addProperty("Password", password);
//
//        Call<JsonObject> call = apiInterface.FTlogin(jsonObject);
//
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                progressDialog.dismiss();
//                Log.d("Login_Response", response + "");
//
//                String response_body=response.body().toString();
//
//                try {
//                    JSONObject jsonObject=new JSONObject(response_body);
//                    String msg=jsonObject.getString("error_msg");
//                    Toast.makeText(NewLoginActivity.this,msg,Toast.LENGTH_SHORT).show();
//                    Intent intent=new Intent(NewLoginActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(NewLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

    private boolean checkValidation(String input) {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();

    }

    public boolean validate(final String password){

        return PASSWORD_PATTERN.matches(password);
    }
    private void find_ids() {
        et_email=findViewById(R.id.et_email);
        et_passsword=findViewById(R.id.et_passsword);
        et_r_password=findViewById(R.id.et_r_password);
        tv_login=findViewById(R.id.tv_login);
    }
}
