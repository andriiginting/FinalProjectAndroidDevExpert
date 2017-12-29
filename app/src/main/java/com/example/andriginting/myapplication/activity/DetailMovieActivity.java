package com.example.andriginting.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.database.MovieHelper;
import com.example.andriginting.myapplication.model.Movie;
import com.example.andriginting.myapplication.util.AppPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

public class DetailMovieActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    TextView judul, tanggalRilis, deskripsi;
    ImageView backdrop;
    Button btnMovieFav;

    Toolbar toolbarDetail;

    private Movie movieItem;
    private int movie_id;
    private ArrayList<Movie> movieArrayList;
    private MovieHelper movieHelper;
    private boolean isFavorite;
    AppPreference appPreference;

    public static String EXTRA_ID = "id";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_MOVIE = "movie";


    String judulFilm, tanggalFilm, deskripsiFilm, gambarFilm;
    int getMovieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getSupportLoaderManager().initLoader(0, null, DetailMovieActivity.this);


        movieHelper = new MovieHelper(this);
        appPreference = new AppPreference(this);
        movieHelper.open();

        toolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbarDetail);
        toolbarDetail.setTitle(DetailMovieActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        judul = findViewById(R.id.title_movie_detail);
        tanggalRilis = findViewById(R.id.date_release_movie_Detail);
        deskripsi = findViewById(R.id.description_movie_detail);
        backdrop = findViewById(R.id.image_back_drop_detail);
        btnMovieFav = findViewById(R.id.btn_movie_favorite);

        Intent getMovieDetail = getIntent();
        judulFilm = getMovieDetail.getStringExtra("judul");
        tanggalFilm = getMovieDetail.getStringExtra("date");
        deskripsiFilm = getMovieDetail.getStringExtra("keterangan");
        gambarFilm = getMovieDetail.getStringExtra("gambar");
        getMovieID = getMovieDetail.getIntExtra("id",0);


        movieItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        getSupportActionBar().setTitle(judulFilm);

        Picasso.with(getApplicationContext())
                .load(IMAGE_URL + gambarFilm)
                .fit()
                .into(backdrop);


        if (movieItem != null) {
            setFavoriteMovie();
            if (savedInstanceState != null) {
                getSupportLoaderManager().initLoader(0, null, this);
            } else {
                getSupportLoaderManager().initLoader(0, null, this);
            }
        }

        btnMovieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite = isFavoriteMovieSucces();
                if (!isFavorite) {
                    Snackbar.make(btnMovieFav, getString(R.string.movie_add_snackbar), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    btnMovieFav.setText(getResources().getString(R.string.btn_non_favorite));
                } else {
                    Snackbar.make(btnMovieFav, getString(R.string.movie_delete_snackbar), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    btnMovieFav.setText(getResources().getString(R.string.btn_favorite));
                }
            }
        });

        judul.setText(judulFilm);
        tanggalRilis.setText(tanggalFilm);
        deskripsi.setText(deskripsiFilm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    void setFavoriteMovie() {
        int movied_id = movieHelper.getData(getMovieID);
        if (movied_id == movieItem.getMovie_id()) {
            btnMovieFav.setText(getResources().getString(R.string.btn_non_favorite));
        } else {
            btnMovieFav.setText(getResources().getString(R.string.btn_favorite));
        }
    }

    public boolean isFavoriteMovieSucces() {
        int movie_db = movieHelper.getData(getMovieID);
        if (movie_db == movieItem.getMovie_id()) {
            movieHelper.delete(getMovieID);
            isFavorite = true;
            return true;
        } else {
            Movie movieItem = new Movie();
            movieItem.setId(getMovieID);
            movieItem.setTitle(judulFilm);
            movieItem.setPosterPath(gambarFilm);
            movieItem.setOverview(deskripsiFilm);
            movieItem.setReleaseDate(tanggalFilm);
            movieHelper.insert(movieItem);
            isFavorite = false;
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {

    }
}
