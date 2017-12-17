package com.example.andriginting.myapplication.network;

import com.example.andriginting.myapplication.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andri Ginting on 12/16/2017.
 */

public interface APIInterface {

    @GET("movie/popular")
    Call<MovieResponse>getPopularMovies(@Query("api_key") String apiKey);

    //untuk search
    @GET("search/movie")
    Call<MovieResponse> getMovieItems (@Query("api_key") String apiKey,@Query("query") String nameMovie);
}
