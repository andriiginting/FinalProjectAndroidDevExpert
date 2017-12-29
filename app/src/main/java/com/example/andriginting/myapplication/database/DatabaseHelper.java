package com.example.andriginting.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andri Ginting on 12/26/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "favorites.db";
    public static String TABLE_NAME = "favorites";
    public static String FIELD_ID = "_id";
    public static String FIELD_MOVIE_ID = "movie_id";
    public static String FIELD_TITLE = "title";
    public static String FIELD_POSTER = "poster";
    public static String FIELD_OVERVIEW = "overview";
    public static String FIELD_RELEASE = "release";

    public static final int DATABASE_VERSION = 2;

    public static String CREATE_TABLE_FAVORITE = "CREATE TABLE "+TABLE_NAME+" ("+
            FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            FIELD_MOVIE_ID+" TEXT NOT NULL, "+
            FIELD_TITLE+" TEXT NOT NULL, "+
            FIELD_POSTER+" TEXT NOT NULL, "+
            FIELD_OVERVIEW+" TEXT NOT NULL, "+
            FIELD_RELEASE+" TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("database", "Success create db ");
        sqLiteDatabase.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
