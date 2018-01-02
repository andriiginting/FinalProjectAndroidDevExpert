package com.example.andriginting.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.adapter.MovieListAdapter;
import com.example.andriginting.myapplication.model.MovieItems;
import com.example.andriginting.myapplication.model.MovieResponse;
import com.example.andriginting.myapplication.network.APIClient;
import com.example.andriginting.myapplication.network.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<MovieItems> movieList = new ArrayList<>();
    RecyclerView recyclerView;
    EditText edtSearchMovie;
    Button btnSearchMovie;

    MovieListAdapter adapter;
    private static final String TAG = "searchMovie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (BuildConfig.API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
        }

        recyclerView = findViewById(R.id.recycler_movie_list);
        edtSearchMovie = findViewById(R.id.edt_search);
        btnSearchMovie = findViewById(R.id.btn_cari_film);
        adapter = new MovieListAdapter(movieList, R.layout.list_kontent, getApplicationContext());
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getPopularMovie();

        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearchMovie.onEditorAction(EditorInfo.IME_ACTION_DONE);
                getMovieSearch();
            }
        });
    }

    //method untuk mencari film
    private void getMovieSearch() {
        String inputan = edtSearchMovie.getText().toString();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        final APIInterface apiInterface = APIClient
                .getRetrofitClient()
                .create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getMovieItems(BuildConfig.API_KEY, inputan);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                if (movieList.size() == 0) {

                    Toast.makeText(getApplicationContext(), "maaf data yang anda cari tidak ditemukan", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: hasil pemanggilan" + call);
                } else {
                    recyclerView.setAdapter(new MovieListAdapter(movieList, R.layout.list_kontent, getApplicationContext()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("erorr", t.getMessage());
            }
        });
    }

    private void getPopularMovie() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        Call<MovieResponse> call = apiInterface.getPopularMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                recyclerView.setAdapter(new MovieListAdapter(movieList, R.layout.list_kontent, getApplicationContext()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
