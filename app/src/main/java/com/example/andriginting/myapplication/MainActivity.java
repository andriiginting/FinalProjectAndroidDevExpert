package com.example.andriginting.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriginting.myapplication.adapter.MovieListAdapter;
import com.example.andriginting.myapplication.model.Movie;
import com.example.andriginting.myapplication.model.MovieResponse;
import com.example.andriginting.myapplication.network.APIClient;
import com.example.andriginting.myapplication.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;
    RecyclerView recyclerView;
    EditText edtSearchMovie;
    Button btnSearchMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(),"Terjadi kesalahan",Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_movie_list);
        edtSearchMovie = findViewById(R.id.edt_search);
        btnSearchMovie = findViewById(R.id.btn_cari_film);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getPopularMovie();

        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovieSearch(edtSearchMovie.getText().toString());
            }
        });
    }

    //method untuk mencari film
    private void getMovieSearch(String inputan){
        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getMovieItems(BuildConfig.API_KEY,inputan);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieListAdapter(movies, R.layout.list_kontent, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("erorr",t.getMessage());
            }
        });
    }

    private void getPopularMovie(){
        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getPopularMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MovieListAdapter(movies, R.layout.list_kontent, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
