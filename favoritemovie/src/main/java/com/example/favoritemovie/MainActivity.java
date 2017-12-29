package com.example.favoritemovie;

import android.content.Intent;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.favoritemovie.adapter.FavoriteAdapter;
import com.example.favoritemovie.model.MovieItem;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,LoaderManager.LoaderCallbacks<Cursor>{

    FavoriteAdapter adapter;
    ListView listView;
    private final int LOAD_FAV = 110;

    private static final String AUTHORITY = "com.example.andriginting.myapplication";
    private static final String BASE_PATH = "favorites";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_fav);
        listView.setOnItemClickListener(this);
        adapter = new FavoriteAdapter(this,null,true);

        listView.setAdapter(adapter);
        getSupportLoaderManager().initLoader(LOAD_FAV,null,MainActivity.this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Cursor cursor = (Cursor) adapter.getItem(i);
        MovieItem movieItem = new MovieItem();
        movieItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
        movieItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
        movieItem.setPoster_path(cursor.getString(cursor.getColumnIndexOrThrow("poster")));
        movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow("overview")));
        movieItem.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow("release")));

        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FAV_ITEM, movieItem);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAV);
    }
}
