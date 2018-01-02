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
import com.example.andriginting.myapplication.model.MovieItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

/**
 * Created by Andri Ginting on 12/23/2017.
 */

public class UpComingPlayingAdapter extends RecyclerView.Adapter<UpComingPlayingAdapter.ViewHolder> {
    private ArrayList<MovieItems> listMovie = new ArrayList<>();
    private Context context;

    public void setMovieItemsList(ArrayList<MovieItems> movies) {
        this.listMovie = movies;
        notifyDataSetChanged();
    }

    public ArrayList<MovieItems> getListMovie() {
        return listMovie;
    }

    public UpComingPlayingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public UpComingPlayingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.konten_coming_playing_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UpComingPlayingAdapter.ViewHolder holder, final int position) {
        final MovieItems posisi = getListMovie().get(position);

        holder.judul.setText(posisi.getTitle());
        holder.deskripsi.setText(posisi.getOverview());
        holder.release.setText(posisi.getRelease_date());
        Picasso.with(context)
                .load(IMAGE_URL + posisi.getPoster_path())
                .resize(350, 450)
                .into(holder.imagePoster);
        holder.detail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                int MOVIE_ID = posisi.getId();
                MovieItems movie = new MovieItems();
                movie.setMovie_id(MOVIE_ID);
                movie.setMovie_id(posisi.getMovie_id());
                movie.setTitle(posisi.getTitle());
                movie.setOverview(posisi.getOverview());
                movie.setPoster_path(posisi.getPoster_path());
                movie.setRelease_date(posisi.getRelease_date());

                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
                context.startActivity(intent);
            }
        }));

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String base_url_info_film = "https://www.themoviedb.org/movie/";

                // share url_info_film menggunakan intent
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, posisi.getTitle());
                view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        TextView judul, deskripsi, release;
        Button share, detail;

        public ViewHolder(View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.image_movie_card_pager_konten);
            judul = itemView.findViewById(R.id.txtJudul_movie_list_pager);
            deskripsi = itemView.findViewById(R.id.txtDeskripsi_movie_list_pager);
            release = itemView.findViewById(R.id.txtTanggal_rilis_movie_list_pager);
            share = itemView.findViewById(R.id.button_share_movie_pager);
            detail = itemView.findViewById(R.id.button_detail_movie_pager);
        }
    }
}
