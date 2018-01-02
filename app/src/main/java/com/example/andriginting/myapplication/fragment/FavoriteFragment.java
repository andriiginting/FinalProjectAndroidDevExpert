package com.example.andriginting.myapplication.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andriginting.myapplication.R;
import com.example.andriginting.myapplication.adapter.UpComingPlayingAdapter;
import com.example.andriginting.myapplication.database.MovieHelper;
import com.example.andriginting.myapplication.model.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    UpComingPlayingAdapter adapter;
    MovieHelper movieHelper;
    ArrayList<MovieItems> moviesData;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_movie_favorite)
    RecyclerView recyclerMovieFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,v);

        recyclerMovieFav.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMovieFav.setHasFixedSize(true);

        adapter = new UpComingPlayingAdapter(getContext());
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();

        moviesData = new ArrayList<>();
        adapter.setMovieItemsList(moviesData);
        recyclerMovieFav.setAdapter(adapter);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (movieHelper!=null){
            movieHelper.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadFavAsync().execute();
    }

    private class LoadFavAsync extends AsyncTask<Void,Void,ArrayList<MovieItems>>{

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... voids) {
            return movieHelper.getAllData();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (moviesData.size()>0){
                moviesData.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> movies) {
            super.onPostExecute(movies);

            moviesData.addAll(movies);
            adapter.setMovieItemsList(moviesData);

            if (moviesData.size() == 0 ){
                Snackbar.make(recyclerMovieFav,"data empty",Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
