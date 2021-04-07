package com.feelfy.feelfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.HorizontalChatModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalChatAdapter extends RecyclerView.Adapter<HorizontalChatAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<HorizontalChatModule> chatMessageInfoArrayList;


    public HorizontalChatAdapter(Context mContext, ArrayList<HorizontalChatModule> chatMessageInfoArrayList) {
        this.mContext = mContext;
        this.chatMessageInfoArrayList = chatMessageInfoArrayList;

    }

    @NonNull
    @Override
    public HorizontalChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row_item_chat_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalChatAdapter.MyViewHolder holder, int position) {
        HorizontalChatModule horizontalChatModule = this.chatMessageInfoArrayList.get(position);

        String image = horizontalChatModule.getImage();
        String userName = horizontalChatModule.getUserName();

        holder.tv_user_name.setText(userName);
//        holder.iv_user_profile_pic.setImageResource(Integer.parseInt(image));
        Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + image).into(holder.iv_user_profile_pic);

    }

    @Override
    public int getItemCount() {
        return chatMessageInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_user_profile_pic;
        TextView tv_user_name;

        MyViewHolder(View view) {
            super(view);
            tv_user_name = view.findViewById(R.id.tv_user_name);
            iv_user_profile_pic = view.findViewById(R.id.iv_user_profile_pic);
        }
    }
}
