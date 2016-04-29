package com.den.net.getmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.den.net.getmovie.model.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {
    private static final String LOG_TAG = "myLog";
    List<MovieItem> movieItemList;
    Context context;
    public static boolean check = false;


    public GridAdapter(List<MovieItem> movieItemList, Context context) {
        this.movieItemList = movieItemList;
        this.context = context;
    }

    public void setData (List<MovieItem> movieItemList){
        this.movieItemList = movieItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieItem movieItem = movieItemList.get(position);
        if (check) {
            String url = movieItem.getFullPosterPath();
            //Log.d(LOG_TAG, "URL = " + url);
            if (!TextUtils.isEmpty(url))
                Picasso.with(context)
                        .load(url)
                        .into(holder.ivPhoto);
        } else {
            holder.ivPhoto.setImageResource(movieItem.photoId);
        }
    }

    @Override
    public int getItemCount() {
        return movieItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }


}