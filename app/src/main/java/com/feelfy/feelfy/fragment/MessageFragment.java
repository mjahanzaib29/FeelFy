package com.feelfy.feelfy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.adapter.MessegesAdapter;
import com.feelfy.feelfy.adapter.ReceivedAdapter;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.modules.Messages;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.shared_pref.AppPreferences;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    public MessageFragment() {
    }

    EditText et_bottom_comments;
    ImageView iv_post_comments, iv_noto_grinning_face,iv_user_profile_pic;
    TextView chatdob;
    RecyclerView messegesRecycler;
    Context mContext;
    List<Messages> messagesList;
    private MessegesAdapter messegesAdapter;
    private ReceivedAdapter receivedAdapter;
    RequestProfileUpdate requestProfileUpdate;
    SharedPreferences preferences ;
    int log_id ;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        messegesRecycler = view.findViewById(R.id.messegesRecycler);
        et_bottom_comments = view.findViewById(R.id.et_bottom_comments);
        iv_post_comments = view.findViewById(R.id.iv_post_comments);
        iv_noto_grinning_face = view.findViewById(R.id.iv_noto_grinning_face);
        iv_user_profile_pic = view.findViewById(R.id.iv_user_profile_pic);
        chatdob = view.findViewById(R.id.chatdob);
        //getting data from preference
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        log_id = Integer.parseInt(preferences.getString("loggin", ""));
        String img = preferences.getString("vimage","");
        String vusernmae = preferences.getString("vusernmae","");
        String vdob = preferences.getString("vdob","");
        Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + img).into(iv_user_profile_pic);
        chatdob.setText(vusernmae+" , "+ getAge(vdob));

        et_bottom_comments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                iv_noto_grinning_face.setVisibility(View.GONE);
                iv_post_comments.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    iv_post_comments.setVisibility(View.GONE);
                    et_bottom_comments.getText().clear();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iv_post_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentmesseges(String.valueOf(et_bottom_comments.getText()));
                et_bottom_comments.setText("");
                et_bottom_comments.getText().clear();
            }
        });
        getmessages();



        refreshHandler.postDelayed(runnable, 3 * 1000);
        return view;
    }

    final Handler refreshHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // do updates for imageview
            getmessages();
            refreshHandler.postDelayed(this, 3 * 1000);
        }
    };



    private void sentmesseges(String messege){
        String matchedid = preferences.getString("vmatchedid","");
        String matchedusername = preferences.getString("vusernmae","");
        requestProfileUpdate= AppPreferences.loadSharedPreferencesLogList(getContext());
        String Log_name = requestProfileUpdate.getNickname();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> responseBodyCall = apiInterface.sendmessege(log_id,Log_name,matchedid,matchedusername,messege);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() != null) {
                    scrollToBottom();
                    getmessages();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void getmessages(){
        String matchedid = preferences.getString("vmatchedid","");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Messages>> call = apiInterface.getMesseges(log_id,matchedid);
        call.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                messagesList = response.body();
                if (messagesList != null){
                    try {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        messegesAdapter = new MessegesAdapter(messagesList, getContext());
                        messegesRecycler.setLayoutManager(linearLayoutManager);
                        messegesRecycler.setAdapter(messegesAdapter);
                        messegesAdapter.notifyDataSetChanged();
                        scrollToBottom();
                        //
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {

            }
        });
    }

    private void scrollToBottom() {
        messegesAdapter.notifyDataSetChanged();
        if (messegesAdapter.getItemCount() > 1)
            messegesRecycler.getLayoutManager().smoothScrollToPosition(messegesRecycler, null, messegesAdapter.getItemCount() - 1);
    }



//    2nd side

    private void getMatchedmessages(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        String matchedid = preferences.getString("vmatchedid","");
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Messages>> call = apiInterface.getMesseges(log_id,matchedid);
        call.enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                messagesList = response.body();
                if (messagesList != null){

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    receivedAdapter = new ReceivedAdapter(messagesList, getActivity().getBaseContext());
                    messegesRecycler.setLayoutManager(linearLayoutManager);
                    messegesRecycler.setAdapter(receivedAdapter);
                    receivedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {

            }
        });
    }

    private String getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return "";

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return String.valueOf(age);
    }
}
