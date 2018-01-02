package com.example.andriginting.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.activity.DetailMovieActivity;
import com.example.andriginting.myapplication.model.MovieItems;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

/**
 * Created by Andri Ginting on 12/16/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{

    private List<MovieItems> movieList;
    private Context context;
    private int list_kontent;

    public MovieListAdapter(List<MovieItems> movieList, int list_kontent, Context context) {
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
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, final int position) {
        holder.judul.setText(movieList.get(position).getTitle());
        holder.keterangan.setText(movieList.get(position).getOverview());
        holder.tanggalRilis.setText(movieList.get(position).getRelease_date());

        Picasso.with(context)
                .load(IMAGE_URL+movieList.get(position).getPoster_path())
                .resize(300,400)
                .into(holder.imageMovie);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("judul",movieList.get(position).getTitle());
                intent.putExtra("keterangan",movieList.get(position).getOverview());
                intent.putExtra("gambar",movieList.get(position).getPoster_path());
                intent.putExtra("date",movieList.get(position).getRelease_date());

                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageMovie;
        TextView judul, keterangan, tanggalRilis;
        LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);

            imageMovie = v.findViewById(R.id.image_movie_list);
            judul = v.findViewById(R.id.txtJudul_movie_list);
            keterangan = v.findViewById(R.id.txtDeskripsi_movie_list);
            tanggalRilis = v.findViewById(R.id.txtTayang_movie_list);
            linearLayout = v.findViewById(R.id.linear_layout_konten);
        }
    }
}
