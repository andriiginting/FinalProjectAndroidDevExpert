package com.example.andriginting.myapplication.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.squareup.picasso.Picasso;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

public class DetailMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    TextView judul,tanggalRilis, deskripsi,rating;
    ImageView backdrop;
    ImageView back;

    double voteRating = 0;
    String url_image = "http://image.tmdb.org/t/p/w342/";

    Toolbar toolbarDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getLoaderManager().initLoader(0,null,DetailMovieActivity.this);

        toolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbarDetail);
        toolbarDetail.setTitle(DetailMovieActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        judul = findViewById(R.id.title_movie_detail);
        tanggalRilis = findViewById(R.id.date_release_movie_Detail);
        deskripsi = findViewById(R.id.description_movie_detail);
        backdrop = findViewById(R.id.image_back_drop_detail);

        Intent getMovieDetail = getIntent();
        String judulFilm = getMovieDetail.getStringExtra("judul");
        String tanggalFilm = getMovieDetail.getStringExtra("date");
        String deskripsiFilm = getMovieDetail.getStringExtra("keterangan");
        String gambarFilm = getMovieDetail.getStringExtra("gambar");
        getSupportActionBar().setTitle(judulFilm);

        Picasso.with(getApplicationContext())
                .load(IMAGE_URL+gambarFilm)
                .fit()
                .into(backdrop);

        judul.setText(judulFilm);
        tanggalRilis.setText(tanggalFilm);
        deskripsi.setText(deskripsiFilm);
//        rating.setText(String.valueOf(ratingFilm));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object o) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
