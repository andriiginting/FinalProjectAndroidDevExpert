package com.example.andriginting.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

/**
 * Created by Andri Ginting on 12/23/2017.
 */

public class UpComingPlayingAdapter  extends RecyclerView.Adapter<UpComingPlayingAdapter.ViewHolder>{
    private List<Movie> movieList;
    private Context context;
    private int konten;

    public UpComingPlayingAdapter(List<Movie> movieList, int konten, Context context) {
        this.movieList = movieList;
        this.context = context;
        this.konten = konten;
    }

    @Override
    public UpComingPlayingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.konten_coming_playing_movie,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UpComingPlayingAdapter.ViewHolder holder, int position) {

        holder.judul.setText(movieList.get(position).getTitle());
        holder.deskripsi.setText(movieList.get(position).getOverview());
        holder.release.setText(movieList.get(position).getReleaseDate());
        Picasso.with(context)
                .load(IMAGE_URL+movieList.get(position).getPosterPath())
                .resize(350,450)
                .into(holder.imagePoster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagePoster;
        TextView judul,deskripsi,release;
        Button share,detail;

        public ViewHolder(View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.image_movie_card_pager_konten);
            judul = itemView.findViewById(R.id.txtJudul_movie_list_pager);
            deskripsi = itemView.findViewById(R.id.txtDeskripsi_movie_list_pager);
            release = itemView.findViewById(R.id.txtTanggal_rilis_movie_list_pager);
            share    = itemView.findViewById(R.id.button_share_movie_pager);
            detail = itemView.findViewById(R.id.button_detail_movie_pager);
        }
    }
}
