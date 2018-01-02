package com.example.andriginting.myapplication.activity;

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
import com.example.andriginting.myapplication.model.MovieItems;
import com.example.andriginting.myapplication.network.DetailLoader;
import com.example.andriginting.myapplication.util.AppPreference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

public class DetailMovieActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    TextView judul, tanggalRilis, deskripsi;
    ImageView backdrop;
    Button btnMovieFav;

    Toolbar toolbarDetail;

    private  MovieItems movieItems;
    private int movie_id;
    private ArrayList<MovieItems> movieArrayList;
    private MovieHelper movieHelper;
    private boolean isFavorite;
    AppPreference appPreference;

    public static String EXTRA_ID = "id";
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_MOVIE = "movie";


    String judulFilm, tanggalFilm, deskripsiFilm, gambarFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movieHelper = new MovieHelper(this);
        appPreference = new AppPreference(this);
        movieHelper.open();

        toolbarDetail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbarDetail);
        toolbarDetail.setTitle(DetailMovieActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieItems = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movie_id =getIntent().getIntExtra(EXTRA_ID,0);

        judul = findViewById(R.id.title_movie_detail);
        tanggalRilis = findViewById(R.id.date_release_movie_Detail);
        deskripsi = findViewById(R.id.description_movie_detail);
        backdrop = findViewById(R.id.image_back_drop_detail);
        btnMovieFav = findViewById(R.id.btn_movie_favorite);


        if (movieItems != null) {
            setFavoriteMovie();
            if (savedInstanceState != null) {

                getSupportLoaderManager().initLoader(0, null, this);
            } else {
                getSupportLoaderManager().initLoader(0, null, this);
            }
        }

        if (movie_id!=0){
            getSupportLoaderManager().initLoader(0,null,this);
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
        int movied_id = movieHelper.getData(movieItems.getMovie_id());
        if (movied_id == movieItems.getMovie_id()) {
            btnMovieFav.setText(getResources().getString(R.string.btn_non_favorite));
        } else {
            btnMovieFav.setText(getResources().getString(R.string.btn_favorite));
        }
    }

    public boolean isFavoriteMovieSucces() {
        int movie_db = movieHelper.getData(movieItems.getMovie_id());
        if (movie_db == movieItems.getMovie_id()) {
            movieHelper.delete(movieItems.getMovie_id());
            isFavorite = true;
            return true;
        } else {
            MovieItems movieItem = new MovieItems();
            movieItem.setId(movieItems.getMovie_id());
            movieItem.setTitle(movieItems.getTitle());
            movieItem.setPoster_path(movieItems.getPoster_path());
            movieItem.setOverview(movieItems.getOverview());
            movieItem.setRelease_date(movieItems.getRelease_date());
            movieHelper.insert(movieItems);
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
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        if (movieItems!=null) {

            return new DetailLoader(getApplicationContext(),movieItems.getMovie_id());
        }else{
            return new DetailLoader(this,movie_id);
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        gambarFilm = data.get(0).getPoster_path();
        judulFilm = data.get(0).getTitle();
        deskripsiFilm = data.get(0).getOverview();
        tanggalFilm = data.get(0).getRelease_date();
        Picasso.with(this)
                .load(IMAGE_URL+gambarFilm)
                .into(backdrop);
        judul.setText(judulFilm);
        deskripsi.setText(deskripsiFilm);
        tanggalRilis.setText(tanggalFilm);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {

    }
}
