package com.example.andriginting.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Andri Ginting on 12/16/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{

    private List<Movie> movieList;
    private Context context;
    private int list_kontent;

    public MovieListAdapter(List<Movie> movieList, int list_kontent, Context context) {
        this.movieList = movieList;
        this.context = context;
        this.list_kontent = list_kontent;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_kontent,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        String url_image = "http://image.tmdb.org/t/p/w342/";

        holder.judul.setText(movieList.get(position).getTitle());
        holder.keterangan.setText(movieList.get(position).getOverview());
        holder.tanggalRilis.setText(movieList.get(position).getReleaseDate());

        Picasso.with(context)
                .load(url_image+movieList.get(position).getPosterPath())
                .resize(300,400)
                .into(holder.imageMovie);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageMovie;
        TextView judul, keterangan, tanggalRilis;

        public ViewHolder(View v) {
            super(v);

            imageMovie = v.findViewById(R.id.image_movie_list);
            judul = v.findViewById(R.id.txtJudul_movie_list);
            keterangan = v.findViewById(R.id.txtDeskripsi_movie_list);
            tanggalRilis = v.findViewById(R.id.txtTayang_movie_list);
        }
    }
}
