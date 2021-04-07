package com.feelfy.feelfy.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.api.ApiClient;
import com.feelfy.feelfy.api.ApiInterface;
import com.feelfy.feelfy.mainactivity.HomeActivity;
import com.feelfy.feelfy.R;
import com.feelfy.feelfy.adapter.HorizontalChatAdapter;
import com.feelfy.feelfy.adapter.VerticalChatAdapter;
import com.feelfy.feelfy.modules.HorizontalChatModule;
import com.feelfy.feelfy.modules.VerticalChatModule;
import com.feelfy.feelfy.profile.ProfileActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView recylerview_horizontal, recylerview_vertical;
    Context mContext;


    public ChatFragment() {
        // Required empty public constructor
    }

    private ArrayList<HorizontalChatModule> chatMessageInfoArrayList = new ArrayList<>();
    private ArrayList<VerticalChatModule> v_chatMessageInfoArrayList = new ArrayList<>();
    private ArrayList<VerticalChatModule> v1_chatMessageInfoArrayList = new ArrayList<>();
    private ArrayList<VerticalChatModule> f1_chatMessageInfoArrayList = new ArrayList<>();
    private ArrayList<VerticalChatModule> v2_chatMessageInfoArrayList = new ArrayList<>();
    private ArrayList<VerticalChatModule> f2_chatMessageInfoArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mContext = getActivity();
        recylerview_horizontal = view.findViewById(R.id.recylerview_horizontal);
        recylerview_vertical = view.findViewById(R.id.recylerview_vertical);

//        chatMessageInfoArrayList.add(new HorizontalChatModule("Jordan", R.drawable.gabriella));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Mike", R.drawable.derick));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Root", R.drawable.gabriella));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Joy", R.drawable.ian));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Elric", R.drawable.sazzad));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Yagi", R.drawable.derick));
//        chatMessageInfoArrayList.add(new HorizontalChatModule("Eric", R.drawable.gabriella));


//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Jason", R.drawable.derick, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Stark", R.drawable.gabriella, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Messi", R.drawable.ian, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Odin", R.drawable.sazzad, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Ozil", R.drawable.derick, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Becham", R.drawable.gabriella, "Feeling has give you, Chat with you!"));
//        v_chatMessageInfoArrayList.add(new VerticalChatModule("Smith", R.drawable.couple, "Feeling has give you, Chat with you!"));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
//        Toast.makeText(mContext, "vchatid:"+log_id, Toast.LENGTH_SHORT).show();
//        setUpRecyclerViewVertical(log_id);
        setUpRecyclerViewHorizontal(log_id);
        v1(log_id);
        v2(log_id);
        return view;
    }

    private void v1(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<VerticalChatModule>> v1 = apiInterface.get_v1(id);

        v1.enqueue(new Callback<ArrayList<VerticalChatModule>>() {
            @Override
            public void onResponse(Call<ArrayList<VerticalChatModule>> call, Response<ArrayList<VerticalChatModule>> response) {
                v1_chatMessageInfoArrayList = response.body();
                f1_chatMessageInfoArrayList.addAll(v1_chatMessageInfoArrayList);
                if(response.body().equals("NO data")){

                }else {
                    mergeall();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<VerticalChatModule>> call, Throwable t) {

            }
        });
    }

    private void v2(int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<VerticalChatModule>> v2 = apiInterface.get_v2(id);

        v2.enqueue(new Callback<ArrayList<VerticalChatModule>>() {
            @Override
            public void onResponse(Call<ArrayList<VerticalChatModule>> call, Response<ArrayList<VerticalChatModule>> response) {
                v2_chatMessageInfoArrayList = response.body();
                f2_chatMessageInfoArrayList.addAll(v2_chatMessageInfoArrayList);
               if(response.body().equals("NO data")){

               }else{
                   mergeall();
               }
            }

            @Override
            public void onFailure(Call<ArrayList<VerticalChatModule>> call, Throwable t) {

            }
        });
    }

    private void mergeall(){
        v_chatMessageInfoArrayList.addAll(f1_chatMessageInfoArrayList);
        v_chatMessageInfoArrayList.addAll(f2_chatMessageInfoArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recylerview_vertical.setLayoutManager(linearLayoutManager);
        VerticalChatAdapter adapter = new VerticalChatAdapter(mContext, v_chatMessageInfoArrayList);
        recylerview_vertical.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new VerticalChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VerticalChatModule verticalChatModule, int position) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("vimage",v_chatMessageInfoArrayList.get(position).getImage());
                editor.putString("vusernmae",v_chatMessageInfoArrayList.get(position).getUserName());
                editor.putString("vdob",v_chatMessageInfoArrayList.get(position).getUnreadmsg());
                editor.putString("vmatchedid",v_chatMessageInfoArrayList.get(position).getCard_user_id());
                editor.apply();

                if (mContext instanceof HomeActivity) {
                    ((HomeActivity) mContext).replaceFragment(new MessageFragment());
                }else if (mContext instanceof ProfileActivity){
                    ((ProfileActivity) mContext).replaceFragment(new MessageFragment());
                }
            }
        });

    }


//        private void setUpRecyclerViewVertical(int id) {
//        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
//            Call<ArrayList<VerticalChatModule>> vchatcall = apiInterface.get_v2(id);
//            vchatcall.enqueue(new Callback<ArrayList<VerticalChatModule>>() {
//                @Override
//                public void onResponse(Call<ArrayList<VerticalChatModule>> call, Response<ArrayList<VerticalChatModule>> response) {
//                    v_chatMessageInfoArrayList = response.body();
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//                    recylerview_vertical.setLayoutManager(linearLayoutManager);
//                    VerticalChatAdapter adapter = new VerticalChatAdapter(mContext, v_chatMessageInfoArrayList);
//                    recylerview_vertical.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    adapter.setOnItemClickListener(new VerticalChatAdapter.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(VerticalChatModule verticalChatModule, int position) {
//                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("vimage",v_chatMessageInfoArrayList.get(position).getImage());
//                            editor.putString("vusernmae",v_chatMessageInfoArrayList.get(position).getUserName());
//                            editor.putString("vdob",v_chatMessageInfoArrayList.get(position).getUnreadmsg());
//                            editor.putString("vmatchedid",v_chatMessageInfoArrayList.get(position).getCard_user_id());
//                            editor.apply();
//
//                            if (mContext instanceof HomeActivity) {
//                                ((HomeActivity) mContext).replaceFragment(new MessageFragment());
//                            }else if (mContext instanceof ProfileActivity){
//                                ((ProfileActivity) mContext).replaceFragment(new MessageFragment());
//                            }
//                        }
//                    });
//
//                }
//
//                @Override
//                public void onFailure(Call<ArrayList<VerticalChatModule>> call, Throwable t) {
//
//                }
//            });
//    }

    private void setUpRecyclerViewHorizontal(int id) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<HorizontalChatModule>> hchatcall = apiInterface.get_Hchat(id);
        hchatcall.enqueue(new Callback<ArrayList<HorizontalChatModule>>() {
            @Override
            public void onResponse(Call<ArrayList<HorizontalChatModule>> call, Response<ArrayList<HorizontalChatModule>> response) {
                chatMessageInfoArrayList = response.body();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recylerview_horizontal.setLayoutManager(linearLayoutManager);
                HorizontalChatAdapter adapter = new HorizontalChatAdapter(mContext, chatMessageInfoArrayList);
                recylerview_horizontal.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<HorizontalChatModule>> call, Throwable t) {

            }
        });

    }

//    private void setUpRecyclerViewVertical() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
//        recylerview_vertical.setLayoutManager(linearLayoutManager);
//        VerticalChatAdapter adapter = new VerticalChatAdapter(mContext, v_chatMessageInfoArrayList);
//        recylerview_vertical.setAdapter(adapter);
//
//        adapter.setOnItemClickListener(new VerticalChatAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(VerticalChatModule verticalChatModule, int position) {
//                if (mContext instanceof HomeActivity) {
//                    ((HomeActivity) mContext).replaceFragment(new MessageFragment());
//                }else if (mContext instanceof ProfileActivity){
//                    ((ProfileActivity) mContext).replaceFragment(new MessageFragment());
//                }
//            }
//        });
//    }

//    private void setUpRecyclerViewHorizontal() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recylerview_horizontal.setLayoutManager(linearLayoutManager);
//        HorizontalChatAdapter adapter = new HorizontalChatAdapter(mContext, chatMessageInfoArrayList);
//        recylerview_horizontal.setAdapter(adapter);
//    }
}
