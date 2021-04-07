package com.feelfy.feelfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.VerticalChatModule;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class VerticalChatAdapter extends RecyclerView.Adapter<VerticalChatAdapter.MyViewHolder> {


    private Context mContext;
    private ArrayList<VerticalChatModule> v_chatMessageInfoArrayList;
    private OnItemClickListener listener;



    public VerticalChatAdapter(Context mContext, ArrayList<VerticalChatModule> v_chatMessageInfoArrayList) {
        this.mContext = mContext;
        this.v_chatMessageInfoArrayList = v_chatMessageInfoArrayList;
    }

    @NonNull
    @Override
    public VerticalChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row_item_chat_adapter_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalChatAdapter.MyViewHolder holder, final int position) {
        final VerticalChatModule verticalChatModule = this.v_chatMessageInfoArrayList.get(position);


        String image = verticalChatModule.getImage();
        String userName = verticalChatModule.getUserName();
        String unread = verticalChatModule.getUnreadmsg();

        holder.tv_user_name.setText(userName);
        holder.tv_unread.setText(unread);
//        holder.iv_user_profile_pic.setImageResource(Integer.parseInt(image));
        Picasso.with(mContext).load("http://dotnetiks.com/feelfy/webServices/" + image).into(holder.iv_user_profile_pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(verticalChatModule, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return v_chatMessageInfoArrayList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(VerticalChatModule verticalChatModule, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_user_profile_pic;
        TextView tv_user_name, tv_unread;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_unread = itemView.findViewById(R.id.tv_unread);
            iv_user_profile_pic = itemView.findViewById(R.id.iv_user_profile_pic);
        }
    }
}
