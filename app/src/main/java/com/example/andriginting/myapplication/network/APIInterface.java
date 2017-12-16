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
    //https://api.themoviedb.org/3/search/movie?api_key=f38bc9d1907b81b6b4f890fc62c73a61&language=en-US&query=it&page=1
    @GET("/search/movie")
    Call<MovieResponse> getMovieItems (@Query("api_key") String api_key,@Query("query") String name_movie);
}
