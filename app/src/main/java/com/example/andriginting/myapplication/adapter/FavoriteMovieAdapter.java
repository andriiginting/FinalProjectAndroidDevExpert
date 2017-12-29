package com.example.andriginting.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

/**
 * Created by Andri Ginting on 12/26/2017.
 */

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder> {

    Context context;
    ArrayList<Movie> movieArrayList;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }


    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.konten_movie_favorite,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FavoriteMovieAdapter.ViewHolder holder, int position) {
        Picasso.with(context)
                .load(IMAGE_URL +movieArrayList.get(position).getPosterPath())
                .into(holder.imageFavorite);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageFavorite;
        public ViewHolder(View itemView) {
            super(itemView);
            imageFavorite = itemView.findViewById(R.id.image_konten_movie_fav);
        }
    }
}
