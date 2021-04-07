package com.feelfy.feelfy.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.Messages;

import java.util.List;

public class MessegesAdapter extends RecyclerView.Adapter<MessegesAdapter.MyMessegesHolder> {

    private List<Messages>messagesList;
    private Context mContext;
    private int SELF = 786;

    public MessegesAdapter(List<Messages> messagesList, Context mContext) {
        this.messagesList = messagesList;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        int log_id = Integer.parseInt(preferences.getString("loggin", ""));
        Messages messages = messagesList.get(position);
        if (messages.getUser_id() == log_id)
        {
            return SELF;
        }
        return position;
    }

    @NonNull
    @Override
    public MyMessegesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == SELF) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_messege, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivedmsg, parent, false);
        }
        return new MyMessegesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMessegesHolder holder, int position) {
        Messages messages = messagesList.get(position);
        holder.single_messege.setText(messages.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MyMessegesHolder extends RecyclerView.ViewHolder {
        private TextView single_messege;
        public MyMessegesHolder(@NonNull View itemView) {
            super(itemView);
            single_messege = (TextView) itemView.findViewById(R.id.single_messege);
        }
    }
}
