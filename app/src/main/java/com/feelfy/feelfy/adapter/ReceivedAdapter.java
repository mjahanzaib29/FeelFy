package com.feelfy.feelfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.Messages;

import java.util.List;

public class ReceivedAdapter extends RecyclerView.Adapter<ReceivedAdapter.ReceivedHolder> {

    private List<Messages> messagesList;
    private Context mContext;

    public ReceivedAdapter(List<Messages> messagesList, Context mContext) {
        this.messagesList = messagesList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ReceivedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivedmsg,parent,false);
        return new ReceivedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedHolder holder, int position) {
        Messages messages = messagesList.get(position);
        holder.single_messegereceived.setText(messages.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ReceivedHolder extends RecyclerView.ViewHolder {
        private TextView single_messegereceived;
        public ReceivedHolder(@NonNull View itemView) {
            super(itemView);
//            single_messegereceived = (TextView) itemView.findViewById(R.id.single_messegereceived);
        }
    }
}
