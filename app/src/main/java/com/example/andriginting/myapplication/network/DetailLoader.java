package com.example.andriginting.myapplication.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.andriginting.myapplication.BuildConfig;
import com.example.andriginting.myapplication.model.Movie;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Andri Ginting on 12/28/2017.
 */

public class DetailLoader extends AsyncTaskLoader<ArrayList<Movie>>{
    private ArrayList<Movie> movieArrayList;
    boolean hasResult = false;
    private int ID_MOVIE;
    public DetailLoader(Context context,int ID_MOVIE) {
        super(context);
        this.ID_MOVIE = ID_MOVIE;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()){
            forceLoad();
        }else if (hasResult){
            deliverResult(movieArrayList);
        }
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {
        movieArrayList = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult){
            onReleaseResources(movieArrayList);
            hasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<Movie> movies) {

    }

    @Override
    public ArrayList<Movie> loadInBackground() {
        final ArrayList<Movie> moviesItem= new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/"+ID_MOVIE+"?api_key="+ BuildConfig.API_KEY+"&language=en-US";
        SyncHttpClient client = new SyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d("TAGLoaderDetail", "onSuccess: "+result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    Movie movieItems = new Movie(responseObject);
                    movieItems.setPosterPath(responseObject.getString("poster_path"));
                    movieItems.setTitle(responseObject.getString("title"));
                    movieItems.setOverview(responseObject.getString("overview"));
                    movieItems.setReleaseDate(responseObject.getString("release_date"));
                    moviesItem.add(movieItems);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("TAGLoaderDetail", "REQUEST FAIL: 1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return moviesItem;
    }
}
