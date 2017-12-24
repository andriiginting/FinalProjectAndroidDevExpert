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

public class UpComingFragment extends Fragment {

    @BindView(R.id.recycler_up_coming_movie)
    RecyclerView recyclerViewUpComing;

    List<Movie> movieList = new ArrayList<>();
    UpComingPlayingAdapter adapter;

    String cariFilm=null;

    public UpComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_up_coming, container, false);
        ButterKnife.bind(this, v);

        setHasOptionsMenu(true);
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UpComingPlayingAdapter(movieList, R.layout.konten_coming_playing_movie, getContext());
        recyclerViewUpComing.setAdapter(adapter);

        if (cariFilm != null){
            loadSearchMovie();
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_ui,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cariFilm = s;
                loadSearchMovie();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            loadUpComingMovies();
    }

    private void loadUpComingMovies() {
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getActivity()));

        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getUpcomingMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                recyclerViewUpComing.setAdapter(new UpComingPlayingAdapter(movieList, R.layout.konten_coming_playing_movie, getContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void loadSearchMovie(){
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    recyclerViewUpComing.setAdapter(new UpComingPlayingAdapter(movieList, R.layout.list_kontent, getContext()));
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
