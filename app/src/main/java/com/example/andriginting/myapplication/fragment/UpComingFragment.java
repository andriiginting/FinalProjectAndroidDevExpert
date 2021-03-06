package com.example.andriginting.myapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import com.example.andriginting.myapplication.model.MovieItems;
import com.example.andriginting.myapplication.model.MovieResponse;
import com.example.andriginting.myapplication.network.APIClient;
import com.example.andriginting.myapplication.network.APIInterface;
import com.example.andriginting.myapplication.network.SearchLoader;
import com.example.andriginting.myapplication.network.UpcomingLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.recycler_up_coming_movie)
    RecyclerView recyclerViewUpComing;

    List<MovieItems> movieList = new ArrayList<>();
    UpComingPlayingAdapter adapter;

    String cariFilm = null;

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

        adapter = new UpComingPlayingAdapter(getContext());
        recyclerViewUpComing.setAdapter(adapter);

        if(savedInstanceState==null){
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }

        return v;
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
                getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>() {
                    @Override
                    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
                        return new SearchLoader(getContext(), cariFilm);
                    }

                    @Override
                    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
                        if (data != null) {
                            adapter.setMovieItemsList(data);
                            recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerViewUpComing.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
                        adapter.setMovieItemsList(null);
                    }
                });
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
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    private void loadUpComingMovies() {
        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getActivity()));

        APIInterface apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);

        Call<MovieResponse> call = apiInterface.getUpcomingMovies(BuildConfig.API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList = response.body().getResults();
                recyclerViewUpComing.setAdapter(new UpComingPlayingAdapter(getContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void loadSearchMovie() {
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
                    recyclerViewUpComing.setAdapter(new UpComingPlayingAdapter(getContext()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("erorr", t.getMessage());
            }
        });
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        return new UpcomingLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        if (data.size() != 0) {
            recyclerViewUpComing.setVisibility(View.VISIBLE);
            adapter.setMovieItemsList(data);
            recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewUpComing.setAdapter(adapter);
        }
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
//        adapter.setMovieItemsList(null);
    }
}
