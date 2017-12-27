package com.example.andriginting.myapplication.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

public class DetailMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    TextView judul,tanggalRilis, deskripsi;
    ImageView backdrop;
    Button btnMovieFav;

    Toolbar toolbarDetail;

    private Movie movieItem;
    private ArrayList<Movie> movieArrayList;
    private MovieHelper movieHelper;
    private boolean isFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        getLoaderManager().initLoader(0,null,DetailMovieActivity.this);


        movieHelper = new MovieHelper(this);
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
        String judulFilm = getMovieDetail.getStringExtra("judul");
        String tanggalFilm = getMovieDetail.getStringExtra("date");
        String deskripsiFilm = getMovieDetail.getStringExtra("keterangan");
        String gambarFilm = getMovieDetail.getStringExtra("gambar");
        getSupportActionBar().setTitle(judulFilm);

        Picasso.with(getApplicationContext())
                .load(IMAGE_URL+gambarFilm)
                .fit()
                .into(backdrop);

        btnMovieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFavorite = isFavoriteMovieSucces();
                if (isFavorite){
                    Snackbar.make(btnMovieFav, getString(R.string.movie_delete_snackbar), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    btnMovieFav.setText(getString(R.string.btn_non_favorite));
                }else{
                    Snackbar.make(btnMovieFav, getString(R.string.movie_delete_snackbar), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    btnMovieFav.setText(getString(R.string.btn_favorite));
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

    void setFavoriteMovie(){
        int movied_id = movieHelper.getData(movieItem.getId());

        if (movied_id == movieItem.getId()){
            btnMovieFav.setText(getResources().getString(R.string.btn_favorite));
        }else{
            btnMovieFav.setText(getResources().getString(R.string.btn_non_favorite));
        }
    }

    public boolean isFavoriteMovieSucces(){
        int movie_db = movieHelper.getData(movieItem.getId());
        if (movie_db == movieItem.getId()){
            movieHelper.delete(movieItem.getId());
            isFavorite = true;
            return true;
        }else{
            Movie movie = new Movie();
            movie.setId(movieItem.getId());
            movie.setTitle(movieItem.getTitle());
            movie.setPosterPath(movieItem.getPosterPath());
            movie.setOverview(movieItem.getOverview());
            movie.setReleaseDate(movieItem.getReleaseDate());
            isFavorite = false;
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper!=null){
            movieHelper.close();
        }
    }
}
