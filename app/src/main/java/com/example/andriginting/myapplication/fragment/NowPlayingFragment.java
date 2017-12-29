package com.example.andriginting.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.adapter.UpComingPlayingAdapter;
import com.example.andriginting.myapplication.model.Movie;
import com.example.andriginting.myapplication.model.MovieResponse;
import com.example.andriginting.myapplication.network.APIClient;
import com.example.andriginting.myapplication.network.APIInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    @BindView(R.id.recycler_now_playing_movie)
    RecyclerView recyclerViewNowPlaying;
    List<Movie> movieList = new ArrayList<>();
    UpComingPlayingAdapter adapter;

    String cariFilm = null;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing,container,false);
        ButterKnife.bind(this,v);

        setHasOptionsMenu(true);
        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UpComingPlayingAdapter(movieList,R.layout.konten_coming_playing_movie,getContext());
        recyclerViewNowPlaying.setAdapter(adapter);
        loadNowPlayingMovie();

        if (cariFilm != null){
            loadSearchNowPlaying();
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadNowPlayingMovie();
    }

    private void loadNowPlayingMovie(){
        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));

        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        Call<MovieResponse> call = apiInterface.getNowPlayingMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList =response.body().getResults();
                recyclerViewNowPlaying.setAdapter(new UpComingPlayingAdapter(movieList,R.layout.konten_coming_playing_movie,getContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_ui, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cariFilm = s;
                loadSearchNowPlaying();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadSearchNowPlaying(){
        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));

        final APIInterface apiInterface = APIClient
                .getRetrofitClient()
                .create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getMovieItems(BuildConfig.API_KEY, cariFilm);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                if (movieList.size() == 0) {

                    Toast.makeText(getContext(), "maaf data yang anda cari tidak ditemukan", Toast.LENGTH_SHORT).show();

                } else {
                    recyclerViewNowPlaying.setAdapter(new UpComingPlayingAdapter(movieList, R.layout.list_kontent, getContext()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("erorr", t.getMessage());
            }
        });
    }
    }

