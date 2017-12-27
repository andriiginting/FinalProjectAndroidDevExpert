package com.example.favoritemovie.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritemovie.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Andri Ginting on 12/27/2017.
 */

public class FavoriteAdapter extends CursorAdapter {
    public static String FIELD_MOVIE_ID = "movie_id";
    public static String FIELD_TITLE = "title";
    public static String FIELD_POSTER = "poster";
    public static String FIELD_OVERVIEW = "overview";
    public static String FIELD_RELEASE = "release";

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.konten_movie_fav, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String urlImage = "http://image.tmdb.org/t/p/w185";
        if (cursor != null) {
            ImageView imgPoster = view.findViewById(R.id.img_item_photo);
            TextView tvMovie_id = view.findViewById(R.id.tv_movie_id);
            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvOverView = view.findViewById(R.id.tv_overview);
            TextView tvRelease = view.findViewById(R.id.tv_release);


            Picasso.with(context)
                    .load(urlImage + cursor.getString(cursor.getColumnIndexOrThrow(FIELD_POSTER)))
                    .into(imgPoster);

            tvMovie_id.setText(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_MOVIE_ID)));
            tvTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_TITLE)));
            tvOverView.setText(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_OVERVIEW)));
            tvRelease.setText(cursor.getString(cursor.getColumnIndexOrThrow(FIELD_RELEASE)));
        }
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
