package com.example.andriginting.myapplication.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.model.MovieItems;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Andri Ginting on 1/2/2018.
 */

public class NowPlayingLoader  extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mDataMovie;
    private boolean hasResult = false;

    public NowPlayingLoader(Context context) {
        super(context);
        onContentChanged();
        Log.d("INIT", "NowPlayingMovieLoader: 1");
    }

    @Override
    protected void onStartLoading() {
        Log.d("Content Changed", "onStartLoading: 1");
        if (takeContentChanged()){
            forceLoad();
        } else if (hasResult){
            deliverResult(mDataMovie);
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieItems> data) {
        mDataMovie = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            onReleaseResources(mDataMovie);
            hasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MovieItems> mDataMovie) {
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        Log.d("LoaderNOwPlaying", "loadInBackground: 1");
        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.API_KEY+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("TAG", "onSuccessNowPlaying: "+result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItemses.add(movieItems);
                        Log.d("TAG", "onSuccessNowPlay: 1");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("TAG", "FAILNowPlay: 1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("TAG", "FAILURENowPlay: 1");
            }
        });

        return movieItemses;
    }
}
