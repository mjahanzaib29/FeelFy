package com.feelfy.feelfy.registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PathDashPathEffect;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.description.AdvancedDiscriptionActivity;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.modules.RegisterResponse;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.modules.UserInfo;
import com.feelfy.feelfy.modules.UserLogin;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView tv_register, tv_by_logging, tv_login, tv_forgotten;
    String s = "By Logging into Feelfy you are accepting our";
    PackageInfo info;
    LoginButton loginButton;
    String name1, fname, lname, Fbemail, social_id, birthday, genderInfo;
    TextView btn_fb_login;
    ApiInterface apiInterface;
   // JsonObject jsonObject;
    EditText et_email, et_passsword;
    RequestProfileUpdate requestProfileUpdate = new RequestProfileUpdate();

    String emailaddress, password;
    String facebookid = "", gender = "", locationx = "", locationy = "", email = "",
            age = "", name = "", showfullname = "", searchbyname = "", res = "", first = "",
            minage = "", maxage = "", minrate = "", maxrate = "", othergender = "";
    Context mContext;
    CallbackManager callbackManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();

        mContext = LoginActivity.this;
        findID();


       /* LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });*/
        changeTextColor();


        try {
            info = getPackageManager().getPackageInfo("com.feelfy.feelfy", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.d("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_gender", "user_friends"));
        //loginButton.setReadPermissions("email");
        //loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                final Toast toast = Toast.makeText(getApplicationContext(),
                        "Cancel", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Facebook error", exception.toString());

                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btn_fb_login) {

                    loginButton.performClick();
                }
            }
        });

    }//=============End Of onCreate()============

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object, GraphResponse response) {
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setCancelable(false); // set cancelable to false
                        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
                        progressDialog.show();
                        Log.e("My Json Response", "" + json_object);
                        Log.e(" Response", "" + response);
                        try {
                            if (json_object != null) {
                                progressDialog.dismiss();
                               String  f_name = json_object.getString("name");
                                // genderInfo = json_object.getString("user_gender");
                                //Log.d("gender",gender);
                                //  Log.d("name1",name1);
                                ////// birthday = json_object.getString("birthday");
//
                                //Log.d("birthday",birthday);

                                String[] str = f_name.split(" ");  //now str[0] is "hello" and str[1] is "goodmorning,2,1"
                                fname = str[0];
                                lname = str[1];
                                social_id = json_object.getString("id");
                                if (json_object.has("email")) {
                                  String  Fb_email = json_object.getString("email");
                                } else {
                                    Fbemail = "";
                                }
                                String Fbimage = "https://graph.facebook.com/" + social_id + "/picture?type=large";

                                facebookid = social_id;
                                name = fname;
                                showfullname = fname + lname;
                               String f_email = Fbemail;

                                Toast.makeText(getApplicationContext(),f_email,Toast.LENGTH_LONG).show();

//                                AppPreferences.savePreferences(LoginActivity.this, "LOGIN_STATUS", "1");
//                                Intent intent = new Intent(mContext, HomeActivity.class);
//                                startActivity(intent);
                                finish();
                                // Login();
                                //Toast.makeText(LoginActivity.this,"dgd"+genderInfo+birthday,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle permission_param = new Bundle();
//        permission_param.putString("fields", "id,name,email,birthday,gender,picture.width(120).height(120)");
        permission_param.putString("fields", "id,name,email,birthday,gender,picture");
        String fields , fbid,name,email,birthday,gender,picture;
        fbid = permission_param.getString("id");
        fields = permission_param.getString("fields");
        name = permission_param.getString("name");
        email = permission_param.getString("email");
//      REGISTERING
        doRegister(email,fbid);
        Toast.makeText(mContext, "field:"+fields+" name:"+name+" email : "+email, Toast.LENGTH_SHORT).show();
        data_request.setParameters(permission_param);
        data_request.executeAsync();
    }

    private void doRegister(final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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
                if (res.equals("1")){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    doLogin(email,password);
                }
                try {
                    requestProfileUpdate.setUser_id(res);
                    AppPreferences.saveSharedPreferencesLogList(getApplicationContext(), requestProfileUpdate);
                    AppPreferences.savePreferences(LoginActivity.this, "LOGIN_STATUS", "1");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("reg",res);
                    editor.apply();
                } catch (NumberFormatException  e) {
                    System.out.println("not a number");
                }
                AppPreferences.saveSharedPreferencesLogList(getBaseContext(), requestProfileUpdate);
                Toast.makeText(LoginActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(LoginActivity.this, AdvancedDiscriptionActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void findID() {
        tv_register = findViewById(R.id.tv_register);
        tv_by_logging = findViewById(R.id.tv_by_logging);
        tv_login = findViewById(R.id.tv_login);
        tv_forgotten = findViewById(R.id.tv_forgotten);
        loginButton = findViewById(R.id.login_button);
        btn_fb_login = findViewById(R.id.btn_fb_login);
        et_email = findViewById(R.id.et_email);
        et_passsword = findViewById(R.id.et_passsword);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTextColor() {
        SpannableString ss = new SpannableString(s);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getColor(R.color.color_login_background));
        ss.setSpan(foregroundColorSpan, 16, 22, Spanned.SPAN_MARK_POINT);
        tv_by_logging.setText(ss);
        onClick();
    }

    private void onClick() {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, NewLoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailaddress = et_email.getText().toString();
                password = et_passsword.getText().toString();

                if (emailaddress.equalsIgnoreCase("")) {
                    et_email.setError("Enter Email OR Phone No.");
                    et_email.requestFocus();
                } else if (!checkValidation(emailaddress)) {
                    et_email.setError("Enter Valid Email or Phone No.");
                    et_email.requestFocus();
                } else if (password.equalsIgnoreCase("")) {
                    et_passsword.setError("Enter Password");
                    et_passsword.requestFocus();
                } /*else if (!validate(password)) {
                    et_passsword.setError("Not a valid password!");
                    et_passsword.requestFocus();
                }*/ else {
                    doLogin(emailaddress, password);
                }
                /*Intent intent = new Intent(mContext, HomeActivity.class);
                mContext.startActivity(intent);*/
            }
        });
        tv_forgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ForgottPasswordActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private boolean checkValidation(String input) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void doLogin(String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.please_wait)); // set message
        progressDialog.show();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<UserLogin> userCall = apiInterface.get_userlogin(email,password);
            userCall.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {

                    if (response.message().equals("OK")){
                        progressDialog.dismiss();
//
                        Log.v("Responsestring", String.valueOf(response.body()));
                       String u_id =(response.body().getId());
                        Log.v("Responsestring", u_id);

                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("reg",u_id);
                        editor.apply();

                        //for getting user info by login id
//                        getinfo(u_id);
                        requestProfileUpdate.setUser_id(u_id);

                        String u_email = response.body().getE_email();
                        requestProfileUpdate.setEmail(u_email);

                        String u_name = response.body().getName();
                        requestProfileUpdate.setNickname(u_name);

                        String u_profilepic = String.valueOf(response.body().getProfile_picture());
                        String u_issub = String.valueOf(response.body().getIsSubscribed());
                        String u_isvisible = String.valueOf(response.body().getIsVisible());
                        String u_usertype = String.valueOf(response.body().getUserType());
                        String u_isactive = String.valueOf(response.body().getIsActive());

//                        if (u_pass!=null) {
//                            requestProfileUpdate.setP(u_pass);
//                        }

                            //requestProfileUpdate.setNickname(u_name);
                            requestProfileUpdate.setName(u_name);

                        if (u_profilepic!=null) {
                            requestProfileUpdate.setProfilephoto(u_profilepic);
                        }
                        if (u_issub!=null) {
                            requestProfileUpdate.setIssubscribed(u_issub);
                        }
                        if (u_isvisible!=null) {
                            requestProfileUpdate.setIsvisible(u_isvisible);
                        }
                        if (u_usertype!=null) {
                            requestProfileUpdate.setUsertype(u_usertype);
                        }
                        if (u_isactive!=null) {
                            requestProfileUpdate.setIsactive(u_isactive);
                        }

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                    AppPreferences.saveSharedPreferencesLogList(mContext, requestProfileUpdate);
                    AppPreferences.savePreferences(LoginActivity.this, "LOGIN_STATUS", "1");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Username & Password not Correct", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Call<JsonObject> call = apiInterface.FTlogin(jsonObject);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                progressDialog.dismiss();
//                Log.d("Login_Response", response + "");
//
//                String response_body = response.body().toString();
//
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response_body);
//                    String msg = jsonObject.getString("error_msg");
//                    JSONArray content = jsonObject.getJSONArray("content");
//                    for (int i = 0; i < content.length(); i++) {
//                        JSONObject jsonObject_array = content.getJSONObject(i);
//                        String gender = jsonObject_array.getString("You");
//                        String id = jsonObject_array.getString("id");
//                        String email = jsonObject_array.getString("email");
//                        String name = jsonObject_array.getString("name");
//                        String Dob = jsonObject_array.getString("Dob");
//                        String profilephoto = jsonObject_array.getString("profilephoto");
//                        String Sexual = jsonObject_array.getString("Sexual");
//                        String Gender_identify = jsonObject_array.getString("Gender_identify");
//                        String Feeling = jsonObject_array.getString("Feeling");
//                        String Fminsearchage = jsonObject_array.getString("Fminsearchage");
//                        String height = jsonObject_array.getString("height");
//                        String Fmaxsearchage = jsonObject_array.getString("Fmaxsearchage");
//                        String Fmindisttance = jsonObject_array.getString("Fmindisttance");
//                        String Fmaxdisttance = jsonObject_array.getString("Fmaxdistance");
//                        String Fsex = jsonObject_array.getString("Fsex");
//                        String Fheight = jsonObject_array.getString("Fheight");
//                        String Fweight = jsonObject_array.getString("Fweight");
//                        String Fecolor = jsonObject_array.getString("Fecolor");
//                        String Fhcolor = jsonObject_array.getString("Fhcolor");
//                        String Dfsmoke = jsonObject_array.getString("Dfsmoke");
//                        String Ddalcohal = jsonObject_array.getString("Ddalcohal");
//                        String Fhchildren = jsonObject_array.getString("Fhchildren");
//                        String Yage = jsonObject_array.getString("Yage");
//                        String Ysex = jsonObject_array.getString("Ysex");
//                        String Ygender = jsonObject_array.getString("Ygender");
//                        String Yheight = jsonObject_array.getString("Yheight");
//                        String Yweight = jsonObject_array.getString("Yweight");
//                        String Yecolor = jsonObject_array.getString("Yecolor");
//                        String Yhcolor = jsonObject_array.getString("Yhcolor");
//                        String Ydsmoke = jsonObject_array.getString("Ydsmoke");
//                        String Ydalcohal = jsonObject_array.getString("Ydalcohal");
//                        String Yhchildren = jsonObject_array.getString("Yhchildren");
//
//
//                        if (Dob!=null) {
//                            requestProfileUpdate.setDob(Dob);
//                        } if (Sexual!=null) {
//                            requestProfileUpdate.setSexual(Sexual);
//                        } if (Gender_identify!=null) {
//                            requestProfileUpdate.setGender_identify(Gender_identify);
//                        } if (profilephoto!=null) {
//                            requestProfileUpdate.setProfilephoto(profilephoto);
//                        }if (Feeling!=null) {
//                            requestProfileUpdate.setFeeling(Feeling);
//                        }if (Fminsearchage!=null) {
//                            requestProfileUpdate.setFminsearchage(Fminsearchage);
//                        }if (height!=null) {
//                            requestProfileUpdate.setHeight(height);
//                        }if (Fmaxsearchage!=null) {
//                            requestProfileUpdate.setFmaxsearchage(Fmaxsearchage);
//                        }if (Fmindisttance!=null) {
//                            requestProfileUpdate.setFmindisttance(Fmindisttance);
//                        }if (Fmaxdisttance!=null) {
//                            requestProfileUpdate.setFmaxdistance(Fmaxdisttance);
//                        }if (Fsex!=null) {
//                            requestProfileUpdate.setFsexString(Fsex);
//                        }if (Fheight!=null) {
//                            requestProfileUpdate.setFheight(Fheight);
//                        }if (Fweight!=null) {
//                            requestProfileUpdate.setFweight(Fweight);
//                        }if (Fecolor!=null) {
//                            requestProfileUpdate.setFecolor(Fecolor);
//                        }if (Fhcolor!=null) {
//                            requestProfileUpdate.setFhcolor(Fhcolor);
//                        }if (Dfsmoke!=null) {
//                            requestProfileUpdate.setDfsmoke(Dfsmoke);
//                        }if (Ddalcohal!=null) {
//                            requestProfileUpdate.setDdalcohal(Ddalcohal);
//                        }if (Fhchildren!=null) {
//                            requestProfileUpdate.setFhchildren(Fhchildren);
//                        }if (Yage!=null) {
//                            requestProfileUpdate.setYage(Yage);
//                        }if (Ysex!=null) {
//                            requestProfileUpdate.setYsex(Ysex);
//                        }if (Ygender!=null) {
//                            requestProfileUpdate.setYgender(Ygender);
//                        }if (Yheight!=null) {
//                            requestProfileUpdate.setYheight(Yheight);
//                        }if (Yweight!=null) {
//                            requestProfileUpdate.setYweight(Yweight);
//                        }if (Yecolor!=null) {
//                            requestProfileUpdate.setYecolor(Yecolor);
//                        }if (Yhcolor!=null) {
//                            requestProfileUpdate.setYhcolor(Yhcolor);
//                        }if (Ydsmoke!=null) {
//                            requestProfileUpdate.setYdsmoke(Ydsmoke);
//                        }if (Ydalcohal!=null) {
//                            requestProfileUpdate.setYdalcohal(Ydalcohal);
//                        }if (Yhchildren!=null) {
//                            requestProfileUpdate.setYhchildren(Yhchildren);
//                        }
//                        requestProfileUpdate.setId(id);
//                        requestProfileUpdate.setEmail(email);
//                        requestProfileUpdate.setName(name);
//                        AppPreferences.saveSharedPreferencesLogList(mContext, requestProfileUpdate);
//                        if (gender.equalsIgnoreCase("undefined")) {
//                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, IdentificationActivity.class);
//                            intent.putExtra("model", requestProfileUpdate);
//                            startActivity(intent);
//                        } else {
//                            AppPreferences.savePreferences(LoginActivity.this, "LOGIN_STATUS", "1");
//                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }

//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

    private void getinfo(String u_id) {
        Call<UserInfo> userInfoCall = apiInterface.get_userinfo(Integer.parseInt(u_id));
        userInfoCall.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                     String gender = response.body().getGender();
                Log.v("Resp", gender);
                     String user_id = response.body().getUser_id();
                     String height = response.body().getHeight();
                     String weight = response.body().getWeight();
                     String eyeColor = response.body().getEyeColor();
                     String hairColor = response.body().getHairColor();
                     String smoke = response.body().getSmoke();
                     String drink = response.body().getDrink();
                     String haveChildren = response.body().getHaveChildren();
                     String nickname = response.body().getNickname();
                     String dob = response.body().getDob();
                     String sexualOrientation = response.body().getSexualOrientation();
                     String description = response.body().getDescription();
                     String sexualPreference = response.body().getSexualPreference();

                //requestProfileUpdate.setGender(user_id);

                if (gender!=null) {
                    requestProfileUpdate.setGender(gender);
                }
                if (height!=null) {
                    requestProfileUpdate.setGender(height);
                }
                if (weight!=null) {
                    requestProfileUpdate.setWeight(weight);
                }
                if (eyeColor!=null) {
                    requestProfileUpdate.setEyeColor(eyeColor);
                }
                if (hairColor!=null) {
                    requestProfileUpdate.setHairColor(hairColor);
                }
                if (smoke!=null) {
                    requestProfileUpdate.setSmoke(smoke);
                }
                if (drink!=null) {
                    requestProfileUpdate.setDrink(drink);
                }
                if (haveChildren!=null) {
                    requestProfileUpdate.setHaveChildren(haveChildren);
                }
                if (nickname!=null) {
                    requestProfileUpdate.setNickname(nickname);
                }
                if (dob!=null) {
                    requestProfileUpdate.setDob(dob);
                }
                if (sexualOrientation!=null) {
                    requestProfileUpdate.setSexualOrientation(sexualOrientation);
                }
                if (description!=null) {
                    requestProfileUpdate.setDescription(description);
                }
                if (sexualPreference!=null) {
                    requestProfileUpdate.setSexualPreference(sexualPreference);
                }


                if (gender.equalsIgnoreCase("undefined")) {
//                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, IdentificationActivity.class);
                    intent.putExtra("model", requestProfileUpdate);
                    startActivity(intent);
                } else {
                    AppPreferences.savePreferences(LoginActivity.this, "LOGIN_STATUS", "1");

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

//
//    private void Login() {
//
//        jsonObject = new JsonObject();
//        jsonObject.addProperty("facebookid", facebookid);
//        jsonObject.addProperty("gender", gender);
//        jsonObject.addProperty("locationx", locationx);
//        jsonObject.addProperty("locationy", locationy);
//        jsonObject.addProperty("email", email);
//        jsonObject.addProperty("age", age);
//        jsonObject.addProperty("name", name);
//        jsonObject.addProperty("showfullname", showfullname);
//        jsonObject.addProperty("searchbyname", searchbyname);
//        jsonObject.addProperty("res", res);
//        jsonObject.addProperty("first", first);
//        jsonObject.addProperty("minage", minage);
//        jsonObject.addProperty("maxage", maxage);
//        jsonObject.addProperty("minrate", minrate);
//        jsonObject.addProperty("maxrate", maxrate);
//        jsonObject.addProperty("othergender", othergender);
//
//        /*final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show();*/
//        apiInterface = ApiClient.getClient().create(ApiInterface.class);
//
//        //Call<LoginModel> call=apiInterface.login(amazonId);
//
//        Call<JsonObject> call = apiInterface.login(jsonObject);
//
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//
//                Log.d("fb_Response", response.body().toString() + "");
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                //progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

}
