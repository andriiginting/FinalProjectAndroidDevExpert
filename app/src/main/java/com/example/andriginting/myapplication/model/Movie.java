package com.example.andriginting.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.andriginting.myapplication.network.APIClient.IMAGE_URL;

/**
 * Created by Andri Ginting on 12/16/2017.
 */

public class Movie implements Parcelable {
    public Movie() {
    }

    private int movie_id;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();

    @SerializedName("id")
    private Integer id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("video")
    private Boolean video;

    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                 String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            int movie_id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("overview");
            double movieRatet = object.getDouble("vote_average");
            String releaseDate = object.getString("release_date");
            String posterUrl = object.getString("poster_path");
            posterUrl = IMAGE_URL + "w185" + posterUrl;
            description = description.length() <= 5 ? description.substring(0, 5) + "..." : description;

            this.id = id;
            this.title = title;
            this.movie_id = movie_id;
            this.overview = description;
            this.releaseDate = releaseDate;
            this.posterPath = posterUrl;
            this.voteAverage = movieRatet;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }


    public String getTitle() {
        return title;
    }


    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    protected Movie(Parcel in) {
        movie_id = in.readInt();
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeInt(id);
        parcel.writeInt(movie_id);
        parcel.writeString(title);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
    }
}
