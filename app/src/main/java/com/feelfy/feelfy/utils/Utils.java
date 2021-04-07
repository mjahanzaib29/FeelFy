package com.feelfy.feelfy.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.modules.CardsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class Utils {

//    private static final String TAG = "Utils";
//    private static String resp;
//
//
//    public static List<CardsResponse> loadProfiles(Context context){
//        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<JSONObject> call = apiInterface.get_card_data();
//        call.enqueue(new Callback<JSONObject>() {
//
//            @Override
//            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
//                CardsResponse cardsResponse = new CardsResponse();
//                String resp = response.body().toString();
//                Log.v("card",resp);
//                cardsResponse.setGetdata(resp);
//
//            }
//
//            @Override
//            public void onFailure(Call<JSONObject> call, Throwable t) {
//
//            }
//        });
//
//        try{
//            CardsResponse cardsResponse = new CardsResponse();
//            GsonBuilder builder = new GsonBuilder();
//            Gson gson = builder.create();
//            JSONObject jsonObject = new JSONObject(cardsResponse.getGetdata());
//            JSONArray array = jsonObject.getJSONArray("data");
//            List<CardsResponse> profileList = new ArrayList<>();
//            for(int i=0;i<array.length();i++){
//                CardsResponse profile = gson.fromJson(array.getString(i), CardsResponse.class);
//                profileList.add(profile);
//            }
//            return profileList;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    private static String loadJSONFromAsset(Context context, String jsonFileName) {
//        String json = null;
//        InputStream is=null;
//        try {
//            AssetManager manager = context.getAssets();
//            Log.d(TAG,"path "+jsonFileName);
//            is = manager.open(jsonFileName);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return json;
//    }
//}
