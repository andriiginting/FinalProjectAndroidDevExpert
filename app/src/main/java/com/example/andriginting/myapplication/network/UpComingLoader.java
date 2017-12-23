package com.example.andriginting.myapplication.network;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.andriginting.myapplication.model.MovieItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.andriginting.myapplication.BuildConfig.API_KEY;

/**
 * Created by Andri Ginting on 12/23/2017.
 */

public class UpComingLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {
    public ArrayList<MovieItem> upcomingData;
    public boolean mHasResult = false;


    public UpComingLoader(final Context context, ArrayList<MovieItem> movieItems) {
        super(context);
        onForceLoad();
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(upcomingData);
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        upcomingData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(upcomingData);
            upcomingData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<MovieItem> data) {
    }


    @Override
    public ArrayList<MovieItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItem> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=en-US";


        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItems = new MovieItem(movie);
                        movieItemses.add(movieItems);
                    }
                    Log.d("REQUEST SUCCESS", "1");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("REQUEST FAILED", "1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
            }
        });


        return movieItemses;
    }
}
