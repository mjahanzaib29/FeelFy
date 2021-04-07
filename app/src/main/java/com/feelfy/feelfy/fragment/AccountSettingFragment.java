package com.feelfy.feelfy.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.modules.RequestProfileUpdate;
import com.feelfy.feelfy.registration.LoginActivity;
import com.feelfy.feelfy.shared_pref.AppPreferences;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.feelfy.feelfy.shared_pref.AppPreferences.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSettingFragment extends Fragment {

    TextView tv_logout,email,delete_account;

    ApiInterface apiInterface;
    public AccountSettingFragment() {
        // Required empty public constructor
    }
    RequestProfileUpdate requestProfileUpdate;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_account_setting, container, false);

        tv_logout=view.findViewById(R.id.tv_logout);
        email=view.findViewById(R.id.email);
        delete_account = view.findViewById(R.id.delete_account);
        requestProfileUpdate=AppPreferences.loadSharedPreferencesLogList(getContext());
         id  = Integer.parseInt(requestProfileUpdate.getUser_id());
       // requestProfileUpdate=LoginActivity.requestProfileUpdate;
        email.setText(requestProfileUpdate.getEmail());

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                AppPreferences.savePreferences(getActivity(), "LOGIN_STATUS", "0");
                getContext().getSharedPreferences(PREFS_NAME,0).edit().clear().commit();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiInterface.get_delete_account(id);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        return view;
    }
}
