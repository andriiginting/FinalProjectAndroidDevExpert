package com.example.andriginting.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.activity.DetailMovieActivity;
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
    public void onBindViewHolder(UpComingPlayingAdapter.ViewHolder holder, final int position) {

        holder.judul.setText(movieList.get(position).getTitle());
        holder.deskripsi.setText(movieList.get(position).getOverview());
        holder.release.setText(movieList.get(position).getReleaseDate());
        Picasso.with(context)
                .load(IMAGE_URL+movieList.get(position).getPosterPath())
                .resize(350,450)
                .into(holder.imagePoster);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("judul",movieList.get(position).getOriginalTitle());
                intent.putExtra("keterangan",movieList.get(position).getOverview());
                intent.putExtra("gambar",movieList.get(position).getPosterPath());
                intent.putExtra("date",movieList.get(position).getReleaseDate());
                view.getContext().startActivity(intent);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String base_url_info_film = "https://www.themoviedb.org/movie/";

                // share url_info_film menggunakan intent
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, base_url_info_film+movieList.get(position).getId());
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
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
