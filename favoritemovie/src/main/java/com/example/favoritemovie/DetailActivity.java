package com.example.favoritemovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritemovie.model.MovieItem;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_FAV_ITEM = "extra_fav_item";
    private ImageView imgPoster;
    private TextView tvTitle, tvOverview, tvRelease, tvId, tvMovieId;

    private MovieItem movieItem = null;
    private boolean isNotEmpty = false;
    String urlImage = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRelease = (TextView) findViewById(R.id.tv_release);
        tvOverview = (TextView) findViewById(R.id.tv_overview);
        imgPoster = (ImageView) findViewById(R.id.img_poster);

        movieItem = getIntent().getParcelableExtra(EXTRA_FAV_ITEM);

        if (movieItem != null) {
            isNotEmpty = true;
            tvTitle.setText(movieItem.getTitle());
            tvOverview.setText(movieItem.getOverview());
            tvRelease.setText(movieItem.getRelease_date());

            Picasso.with(this)
                    .load(urlImage+movieItem.getPoster_path())
                    .into(imgPoster);
        }
    }
}
