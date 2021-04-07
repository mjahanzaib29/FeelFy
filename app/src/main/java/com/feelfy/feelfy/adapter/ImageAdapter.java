package com.feelfy.feelfy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelfy.feelfy.R;
import com.feelfy.feelfy.modules.HorizontalImageModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<HorizontalImageModule> horizontalImageModules;

    public ImageAdapter(Context mContext, ArrayList<HorizontalImageModule> horizontalImageModules) {
        this.mContext = mContext;
        this.horizontalImageModules = horizontalImageModules;

    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_row_item_image_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        HorizontalImageModule horizontalImageModule = horizontalImageModules.get(position);
        String image = horizontalImageModule.getPhotopath();
        //int ic_vector_add = horizontalImageModule.getIc_vector_add();
        //int count = 2131099789;

       /* if (image == count){
            holder.image_add.setVisibility(View.GONE);
        }*/
        Picasso.with(mContext).load("http://13.232.184.108:3000/images/"+image).into(holder.image_camera);
       // holder.image_camera.setImageResource(http://13.232.184.108:3000/images/fd13d2bdcd28a56ed85dc92f165364b5.jpg);
       // holder.image_add.setImageResource(ic_vector_add);
    }

    @Override
    public int getItemCount() {
        return horizontalImageModules.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_camera, image_add;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_camera = itemView.findViewById(R.id.image_camera);
          //  image_add = itemView.findViewById(R.id.image_add);
        }
    }
}
