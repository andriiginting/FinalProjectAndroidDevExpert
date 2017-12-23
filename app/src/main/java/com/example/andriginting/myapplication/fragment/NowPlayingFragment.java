package com.example.andriginting.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing,container,false);
        ButterKnife.bind(this,v);

        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UpComingPlayingAdapter(movieList,R.layout.konten_coming_playing_movie,getContext());
        recyclerViewNowPlaying.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadNowPlayingMoviw();
    }

    private void loadNowPlayingMoviw(){
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
}
